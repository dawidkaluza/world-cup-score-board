package pl.dkaluza;

public class ScoreboardService {
    private final Scoreboard scoreboard;

    public ScoreboardService(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public ScoreboardService() {
        this.scoreboard = new Scoreboard();
    }

    public void startGame(String homeTeam, String awayTeam) throws ValidationException, GameAlreadyExistsException {
        Game game = new Game(homeTeam, awayTeam);
        scoreboard.addGame(game);
    }
}
