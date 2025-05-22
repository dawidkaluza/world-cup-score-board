package pl.dkaluza;

public class Game {
    private final String homeTeamName;
    private final String awayTeamName;

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

    public String homeTeamName() {
        return homeTeamName;
    }

    public String awayTeamName() {
        return awayTeamName;
    }

    public int homeTeamScore() {
        throw new UnsupportedOperationException();
    }

    public int awayTeamScore() {
        throw new UnsupportedOperationException();
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
}
