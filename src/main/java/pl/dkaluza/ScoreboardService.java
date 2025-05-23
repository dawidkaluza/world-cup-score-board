package pl.dkaluza;

import java.util.List;

public class ScoreboardService {
    private final Scoreboard scoreboard;

    ScoreboardService(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public ScoreboardService() {
        this.scoreboard = new Scoreboard();
    }

    /// Starts a new game and adds it to the scoreboard.
    /// @throws ValidationException if team names are invalid.
    /// @throws GameAlreadyExistsException if the game already exists in the scoreboard.
    public void startGame(String homeTeam, String awayTeam) throws ValidationException, GameAlreadyExistsException {
        Game game = new Game(homeTeam, awayTeam);
        scoreboard.addGame(game);
    }

    public void updateScore(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore)
        throws GameNotFoundException, ValidationException {
        Assertions.argumentNotNull(homeTeamName);
        Assertions.argumentNotNull(awayTeamName);

        Game game = scoreboard.findGameByTeams(homeTeamName, awayTeamName)
            .orElseThrow(() -> new GameNotFoundException("Game not found"));

        game.setHomeTeamScore(homeTeamScore);
        game.setAwayTeamScore(awayTeamScore);
    }

    public void finishGame(String homeTeam, String awayTeam) throws GameNotFoundException {
        Assertions.argumentNotNull(homeTeam);
        Assertions.argumentNotNull(awayTeam);

        Game game = scoreboard.findGameByTeams(homeTeam, awayTeam)
            .orElseThrow(() -> new GameNotFoundException("Game not found"));

        scoreboard.removeGame(game);
    }

    /// Returns a summary of games on the scoreboard, ordered by: total score descending, start time descending.
    /// @return summary of games.
    public List<Game> getSummaryOfGames() {
        return scoreboard.getSummary();
    }
}
