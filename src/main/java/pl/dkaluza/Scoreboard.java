package pl.dkaluza;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    /// Returns a summary of games on the scoreboard, ordered by: total score descending, start time descending.
    /// @return summary of games.
    public List<Game> getSummary() {
        List<Game> games = new ArrayList<>(this.games);
        games.reversed().sort(Comparator.comparingInt(Game::totalScore));
        return games;
    }
}
