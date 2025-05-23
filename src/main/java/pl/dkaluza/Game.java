package pl.dkaluza;

public class Game {
    private final GameId id;
    private int homeTeamScore;
    private int awayTeamScore;

    Game(GameId id) {
        this.id = id;
    }

    private static boolean isScoreInvalid(int score) {
        return score < 0;
    }

    public GameId id() {
        return id;
    }

    public int homeTeamScore() {
        return homeTeamScore;
    }

    void setHomeTeamScore(int homeTeamScore) throws ValidationException {
        if (isScoreInvalid(homeTeamScore)) {
            throw new ValidationException("Score must be a positive value");
        }

        this.homeTeamScore = homeTeamScore;
    }

    public int awayTeamScore() {
        return awayTeamScore;
    }

    void setAwayTeamScore(int awayTeamScore) throws ValidationException {
        if (isScoreInvalid(awayTeamScore)) {
            throw new ValidationException("Score must be a positive value");
        }

        this.awayTeamScore = awayTeamScore;
    }

    public int totalScore() {
        return homeTeamScore + awayTeamScore;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Game game)) {
            return false;
        }

        return id.equals(game.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Game{" +
               "id=" + id +
               ", homeTeamScore=" + homeTeamScore +
               ", awayTeamScore=" + awayTeamScore +
               '}';
    }
}
