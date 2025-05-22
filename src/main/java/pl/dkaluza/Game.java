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
}
