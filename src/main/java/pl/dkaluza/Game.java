package pl.dkaluza;

class Game {
    private final String homeTeamName;
    private final String awayTeamName;

    public Game(String homeTeamName, String awayTeamName) throws ValidationException {
        if (!isTeamNameValid(homeTeamName) || !isTeamNameValid(awayTeamName)) {
            throw new ValidationException("Team names must bo non-blank strings");
        }

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    private static boolean isTeamNameValid(String teamName) {
        return teamName != null && !teamName.isBlank();
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
