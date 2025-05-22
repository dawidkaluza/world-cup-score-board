package pl.dkaluza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @Test
    void startGame_gameAlreadyExists_throwException() throws ValidationException, GameAlreadyExistsException {
        ScoreboardService scoreboardService = new ScoreboardService();
        scoreboardService.startGame("Poland", "Germany");

        GameAlreadyExistsException exception = catchThrowableOfType(
            GameAlreadyExistsException.class,
            () -> scoreboardService.startGame("Poland", "Germany")
        );

        assertThat(exception)
            .isNotNull();
    }

    void startGame_validNewGame_addGameToScoreboard() {

    }
}