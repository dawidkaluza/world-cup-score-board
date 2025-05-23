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

    public void finishGame(String homeTeam, String awayTeam) throws ValidationException {

    }

    /// Returns a summary of games on the scoreboard, ordered by: total score descending, start time descending.
    /// @return summary of games.
    public List<Game> getSummaryOfGames() {
        return scoreboard.getSummary();
    }
}
