package pl.dkaluza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ScoreboardServiceTest {
    @ParameterizedTest
    @CsvSource(value = {
        "NULL, NULL",
        "NULL, Poland",
        "Argentina, NULL",
        "'', Germany",
    }, nullValues = "NULL")
    void startGame_invalidTeamNames_throwException(String homeTeam, String awayTeam) {
        ScoreboardService scoreboardService = new ScoreboardService();

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
        ScoreboardService scoreboardService = new ScoreboardService();
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

    void startGame_validNewGame_addGameToScoreboard() {

    }
}