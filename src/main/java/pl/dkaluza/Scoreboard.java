package pl.dkaluza;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class Scoreboard {
    private final List<Game> games;

    public Scoreboard() {
        this.games = new ArrayList<>();
    }

    /// Adds a new game to the scoreboard.
    /// @throws GameAlreadyExistsException if given game already exists.
    public void addGame(Game game) throws GameAlreadyExistsException {
        if (games.contains(game)) {
            throw new GameAlreadyExistsException("Given game already exists");
        }

        games.add(game);
    }

    // TODO update params to accept GameId only
    public Optional<Game> findGameByTeams(String homeTeamName, String awayTeamName) {
        return games.stream()
            .filter(game -> game.id().homeTeam().equals(homeTeamName) && game.id().awayTeam().equals(awayTeamName))
            .findAny();
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    /// Returns a summary of games on the scoreboard, ordered by: total score descending, start time descending.
    /// @return summary of games.
    public List<Game> getSummary() {
        List<Game> reversedGames = new ArrayList<>(this.games.reversed());
        reversedGames.sort(Comparator.comparingInt(Game::totalScore).reversed());
        return reversedGames;
    }
}
