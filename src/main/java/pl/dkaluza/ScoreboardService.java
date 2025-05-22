package pl.dkaluza;

public class ScoreboardService {
    public void startGame(String homeTeam, String awayTeam) throws ValidationException {
        Game game = new Game(homeTeam, awayTeam);
    }
}
