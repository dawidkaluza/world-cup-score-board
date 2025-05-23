package pl.dkaluza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ScoreboardServiceTest {
    private Scoreboard scoreboard;
    private ScoreboardService scoreboardService;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
        scoreboardService = new ScoreboardService(scoreboard);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "NULL, NULL",
        "NULL, Poland",
        "Argentina, NULL",
    }, nullValues = "NULL")
    void startGame_nullParams_throwException(String homeTeam, String awayTeam) {
        IllegalArgumentException exception = catchThrowableOfType(
            IllegalArgumentException.class,
            () -> scoreboardService.startGame(homeTeam, awayTeam)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "'', Germany",
        "'', ''",
        "Germany, ''",
        "Germany, Germany",
    }, nullValues = "NULL")
    void startGame_invalidTeamNames_throwException(String homeTeam, String awayTeam) {
        ValidationException exception = catchThrowableOfType(
            ValidationException.class,
            () -> scoreboardService.startGame(homeTeam, awayTeam)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @MethodSource("startGameWhenGameAlreadyExistsParamsProvider")
    void startGame_gameAlreadyExists_throwException(List<Map.Entry<String, String>> existingGamesList) throws ValidationException, GameAlreadyExistsException {
        for (var entry : existingGamesList) {
            scoreboardService.startGame(entry.getKey(), entry.getValue());
        }

        GameAlreadyExistsException exception = catchThrowableOfType(
            GameAlreadyExistsException.class,
            () -> scoreboardService.startGame("Poland", "Germany")
        );

        assertThat(exception)
            .isNotNull();
    }

    private static Stream<Arguments> startGameWhenGameAlreadyExistsParamsProvider() {
        return Stream.of(
            Arguments.of(List.of(
                Map.entry("Poland", "Germany")
            )),
            Arguments.of(List.of(
                Map.entry("Germany", "Poland"),
                Map.entry("Poland", "Germany"),
                Map.entry("Brazil", "USA")
            )),
            Arguments.of(List.of(
                Map.entry("Poland", "Germany"),
                Map.entry("England", "Spain")
            )),
            Arguments.of(List.of(
                Map.entry("England", "Spain"),
                Map.entry("Poland", "Germany")
            ))
        );
    }

    @ParameterizedTest
    @MethodSource("startValidNewGameParamsProvider")
    void startGame_validNewGame_gameAddedToTheScoreboard(List<Map.Entry<String, String>> existingGamesList) throws ValidationException, GameAlreadyExistsException {
        for (var entry : existingGamesList) {
            scoreboardService.startGame(entry.getKey(), entry.getValue());
        }

        scoreboardService.startGame("Poland", "Germany");

        Game game = scoreboardService.getSummaryOfGames()
            .stream()
            .filter(filteredGame ->
                filteredGame.id().equals(new GameId("Poland", "Germany"))
            ).findAny().orElse(null);

        assertThat(game)
            .isNotNull();

        assertThat(game.homeTeamScore())
            .isEqualTo(0);

        assertThat(game.awayTeamScore())
            .isEqualTo(0);
    }

    private static Stream<Arguments> startValidNewGameParamsProvider() {
        return Stream.of(
            Arguments.of(List.of()),
            Arguments.of(List.of(
                Map.entry("Germany", "Poland"),
                Map.entry("Brazil", "USA")
            )),
            Arguments.of(List.of(
                Map.entry("Brazil", "Chile"),
                Map.entry("Germany", "Poland")
            ))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "NULL, Poland",
        "NULL, NULL",
        "Poland, NULL",
    }, nullValues = "NULL")
    void updateScore_nullParams_throwException(String homeTeamName, String awayTeamName)
        throws GameAlreadyExistsException, ValidationException {
        scoreboardService.startGame("Poland", "Germany");
        scoreboardService.startGame("England", "Croatia");
        scoreboardService.startGame("Italy", "Portugal");

        IllegalArgumentException exception = catchThrowableOfType(
            IllegalArgumentException.class,
            () -> scoreboardService.updateScore(homeTeamName, awayTeamName, 1, 1)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
        "Germany, Poland",
        "England, Germany",
    })
    void updateScore_gameNotFound_throwException(String homeTeamName, String awayTeamName) throws GameAlreadyExistsException, ValidationException {
        scoreboardService.startGame("Poland", "Germany");
        scoreboardService.startGame("England", "Croatia");
        scoreboardService.startGame("Italy", "Portugal");

        GameNotFoundException exception = catchThrowableOfType(
            GameNotFoundException.class,
            () -> scoreboardService.updateScore(homeTeamName, awayTeamName, 1, 1)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
        "Poland, Germany, -1, 0",
        "England, Croatia, 3, -1",
        "Italy, Portugal, -1, -4",
    })
    void updateScore_invalidScore_throwException(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore)
        throws GameAlreadyExistsException, ValidationException {
        scoreboardService.startGame("Poland", "Germany");
        scoreboardService.startGame("England", "Croatia");
        scoreboardService.startGame("Italy", "Portugal");

        ValidationException exception = catchThrowableOfType(
            ValidationException.class,
            () -> scoreboardService.updateScore(homeTeamName, awayTeamName, homeTeamScore, awayTeamScore)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
        "Poland, Germany, 0, 2",
        "England, Croatia, 4, 0",
        "Italy, Portugal, 0, 0",
    })
    void updateScore_validUpdate_scoreboardUpdated(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
        throws GameAlreadyExistsException, ValidationException, GameNotFoundException {
        scoreboardService.startGame("Poland", "Germany");
        scoreboardService.startGame("England", "Croatia");
        scoreboardService.startGame("Italy", "Portugal");

        scoreboardService.updateScore(homeTeam, awayTeam, homeTeamScore, awayTeamScore);

        Optional<Game> optionalGame = scoreboard.findGameById(new GameId(homeTeam, awayTeam));
        assertThat(optionalGame)
            .isPresent();

        Game game = optionalGame.get();

        assertThat(game.homeTeamScore())
            .isEqualTo(homeTeamScore);

        assertThat(game.awayTeamScore())
            .isEqualTo(awayTeamScore);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "NULL, Poland",
        "Poland, NULL",
        "NULL, NULL",
    }, nullValues = "NULL")
    void finishGame_nullParams_throwException(String homeTeamName, String awayTeamName) {
        IllegalArgumentException exception = catchThrowableOfType(
            IllegalArgumentException.class,
            () -> scoreboardService.finishGame(homeTeamName, awayTeamName)
        );

        assertThat(exception)
            .isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
        "Panama, Ecuador",
        "Poland, Croatia",
    })
    void finishGame_gameNotFound_throwException(String homeTeamName, String awayTeamName) throws GameAlreadyExistsException, ValidationException {
        scoreboardService.startGame("Ecuador", "Panama");

        GameNotFoundException exception = catchThrowableOfType(
            GameNotFoundException.class,
            () -> scoreboardService.finishGame(homeTeamName, awayTeamName)
        );

        assertThat(exception)
            .isNotNull();
    }


    @ParameterizedTest
    @MethodSource("finishGameFoundGameParamsProvider")
    void finishGame_foundGame_gameRemovedFromScoreboard(List<Map.Entry<String, String>> gamesToStart, Map.Entry<String, String> gameToFinish)
        throws GameAlreadyExistsException, ValidationException, GameNotFoundException {
        for (var gameToStart : gamesToStart) {
            scoreboardService.startGame(gameToStart.getKey(), gameToStart.getValue());
        }

        scoreboardService.finishGame(gameToFinish.getKey(), gameToFinish.getValue());

        assertThat(scoreboard.findGameById(new GameId(gameToFinish.getKey(), gameToFinish.getValue())))
            .isEmpty();
    }

    // TODO move into CsvSource
    private static Stream<Arguments> finishGameFoundGameParamsProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    Map.entry("Poland", "Germany"),
                    Map.entry("England", "Croatia"),
                    Map.entry("Italy", "Portugal")
                ),
                Map.entry("Poland", "Germany")
            ),
            Arguments.of(
                List.of(
                    Map.entry("Poland", "Germany"),
                    Map.entry("England", "Croatia"),
                    Map.entry("Italy", "Portugal")
                ),
                Map.entry("England", "Croatia")
            ),
            Arguments.of(
                List.of(
                    Map.entry("Poland", "Germany"),
                    Map.entry("England", "Croatia"),
                    Map.entry("Italy", "Portugal")
                ),
                Map.entry("Italy", "Portugal")
            )
        );
    }

    @Test
    void getSummaryOfGames_noGamesAdded_returnEmptySummary() {
        List<Game> games = scoreboardService.getSummaryOfGames();

        assertThat(games)
            .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("getSummaryOfGamesParamsProvider")
    void getSummaryOfGames_variousGamesAdded_returnInExpectedOrder(List<Game> addedGames, List<Game> expectedSummary)
        throws GameAlreadyExistsException {
        for (var game : addedGames) {
            scoreboard.addGame(game);
        }

        List<Game> actualSummary = scoreboardService.getSummaryOfGames();

        assertThat(actualSummary)
            .isEqualTo(expectedSummary);
    }

    private static Stream<Arguments> getSummaryOfGamesParamsProvider() throws ValidationException {
        return Stream.of(
            Arguments.of(
                List.of(
                    createGame("Poland", "Germany", 1, 1),
                    createGame("Brazil", "USA", 3, 2),
                    createGame("England", "Croatia", 4, 4)
                ),
                List.of(
                    createGame("England", "Croatia", 4, 4),
                    createGame("Brazil", "USA", 3, 2),
                    createGame("Poland", "Germany", 1, 1)
                )
            ),
            Arguments.of(
                List.of(
                    createGame("Poland", "Germany", 1, 1),
                    createGame("Brazil", "USA", 1, 1),
                    createGame("England", "Croatia", 1, 1)
                ),
                List.of(
                    createGame("England", "Croatia", 1, 1),
                    createGame("Brazil", "USA", 1, 1),
                    createGame("Poland", "Germany", 1, 1)
                )
            ),
            Arguments.of(
                List.of(
                    createGame("Poland", "Germany", 4, 0),
                    createGame("Brazil", "USA", 0, 0),
                    createGame("England", "Croatia", 0, 3),
                    createGame("Japan", "Egypt", 0, 0)
                ),
                List.of(
                    createGame("Poland", "Germany", 4, 0),
                    createGame("England", "Croatia", 0, 3),
                    createGame("Japan", "Egypt", 0, 0),
                    createGame("Brazil", "USA", 0, 0)
                )
            )
        );
    }

    private static Game createGame(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore)
        throws ValidationException {
        var gameId = new GameId(homeTeamName, awayTeamName);
        var game = new Game(gameId);
        game.setHomeTeamScore(homeTeamScore);
        game.setAwayTeamScore(awayTeamScore);
        return game;
    }
}