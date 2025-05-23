package pl.dkaluza;

public class GameId {
    private final String homeTeam;
    private final String awayTeam;

    GameId(String homeTeam, String awayTeam) throws ValidationException {
        if (isTeamNameInvalid(homeTeam) || isTeamNameInvalid(awayTeam)) {
            throw new ValidationException("Team names must bo non-blank strings");
        }

        if (homeTeam.equals(awayTeam)) {
            throw new ValidationException("Home and away team must be different");
        }

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    private static boolean isTeamNameInvalid(String teamName) {
        return teamName.isBlank();
    }

    public String homeTeam() {
        return homeTeam;
    }

    public String awayTeam() {
        return awayTeam;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof GameId gameId)) {
            return false;
        }

        return homeTeam.equals(gameId.homeTeam) && awayTeam.equals(gameId.awayTeam);
    }

    @Override
    public int hashCode() {
        int result = homeTeam.hashCode();
        result = 31 * result + awayTeam.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GameId{" +
               "homeTeam='" + homeTeam + '\'' +
               ", awayTeam='" + awayTeam + '\'' +
               '}';
    }
}
