package pl.dkaluza;

public class Game {
    private final String homeTeamName;
    private final String awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;

    public Game(String homeTeamName, String awayTeamName) throws ValidationException {
        if (!isTeamNameValid(homeTeamName) || !isTeamNameValid(awayTeamName)) {
            throw new ValidationException("Team names must bo non-blank strings");
        }

        if (homeTeamName.equals(awayTeamName)) {
            throw new ValidationException("Home and away team must must be different");
        }

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    private static boolean isTeamNameValid(String teamName) {
        return teamName != null && !teamName.isBlank();
    }

    private static boolean isScoreValid(int score) {
        return score >= 0;
    }

    public String homeTeamName() {
        return homeTeamName;
    }

    public String awayTeamName() {
        return awayTeamName;
    }

    public int homeTeamScore() {
        return homeTeamScore;
    }

    void setHomeTeamScore(int homeTeamScore) throws ValidationException {
        if (!isScoreValid(homeTeamScore)) {
            throw new ValidationException("Score must be a positive value");
        }

        this.homeTeamScore = homeTeamScore;
    }

    public int awayTeamScore() {
        return awayTeamScore;
    }

    void setAwayTeamScore(int awayTeamScore) throws ValidationException {
        if (!isScoreValid(awayTeamScore)) {
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

        return homeTeamName.equals(game.homeTeamName) && awayTeamName.equals(game.awayTeamName);
    }

    @Override
    public int hashCode() {
        int result = homeTeamName.hashCode();
        result = 31 * result + awayTeamName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
               homeTeamName + " " + homeTeamScore + ":" + awayTeamScore + " " + awayTeamName +
       '}';
    }
}
