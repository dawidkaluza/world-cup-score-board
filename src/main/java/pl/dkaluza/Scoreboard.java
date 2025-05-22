package pl.dkaluza;

import java.util.ArrayList;
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

    public List<Game> getSummary() {
        return games;
    }
}
