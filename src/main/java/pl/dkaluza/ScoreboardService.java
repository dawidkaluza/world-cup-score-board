package pl.dkaluza;

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
}
