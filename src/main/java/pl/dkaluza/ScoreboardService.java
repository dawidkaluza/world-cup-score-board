package pl.dkaluza;

import java.util.List;

public class ScoreboardService {
    private final Scoreboard scoreboard;

    ScoreboardService(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    /// Starts a new game and adds it to the scoreboard.
    /// @param homeTeam home team name
    /// @param awayTeam away team name
    /// @throws IllegalArgumentException if params are null.
    /// @throws ValidationException if team names are invalid.
    /// @throws GameAlreadyExistsException if the game already exists on the scoreboard.
    /// @throws TeamAlreadyPlaysException if one of given teams already plays in some other game.
    public void startGame(String homeTeam, String awayTeam) throws ValidationException, GameAlreadyExistsException, TeamAlreadyPlaysException {
        Assertions.argumentNotNull(homeTeam);
        Assertions.argumentNotNull(awayTeam);

        var gameId = new GameId(homeTeam, awayTeam);
        var game = new Game(gameId);
        scoreboard.addGame(game);
    }

    /// Updates game score on the scoreboard.
    /// @param homeTeam home team name
    /// @param awayTeam away team name
    /// @param homeTeamScore new home team score
    /// @param awayTeamScore new away team score
    /// @throws IllegalArgumentException if params are null.
    /// @throws ValidationException if teams names or new score are not valid.
    /// @throws GameNotFoundException if there is no game on the scoreboard for given teams.
    public void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
        throws GameNotFoundException, ValidationException {
        Assertions.argumentNotNull(homeTeam);
        Assertions.argumentNotNull(awayTeam);

        var gameId = new GameId(homeTeam, awayTeam);
        Game game = scoreboard.findGameById(gameId)
            .orElseThrow(() -> new GameNotFoundException("Game not found"));

        game.setHomeTeamScore(homeTeamScore);
        game.setAwayTeamScore(awayTeamScore);
    }

    /// Finishes a game and removes it from the scoreboard.
    /// @param homeTeam home team name
    /// @param awayTeam away team name
    /// @throws IllegalArgumentException if params are null.
    /// @throws ValidationException if teams names are not valid.
    /// @throws GameNotFoundException if there is no game on the scoreboard for given teams.
    public void finishGame(String homeTeam, String awayTeam) throws ValidationException, GameNotFoundException {
        Assertions.argumentNotNull(homeTeam);
        Assertions.argumentNotNull(awayTeam);

        var gameId = new GameId(homeTeam, awayTeam);
        Game game = scoreboard.findGameById(gameId)
            .orElseThrow(() -> new GameNotFoundException("Game not found"));

        scoreboard.removeGame(game);
    }

    /// Returns a summary of games on the scoreboard, ordered by: total score descending, start time descending.
    /// @return summary of games.
    public List<Game> getSummaryOfGames() {
        return scoreboard.getSummary();
    }
}
