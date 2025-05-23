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

    public void addGame(Game game) throws GameAlreadyExistsException {
        if (games.contains(game)) {
            throw new GameAlreadyExistsException("Given game already exists");
        }

        games.add(game);
    }

    public Optional<Game> findGameById(GameId id) {
        return games.stream()
            .filter(game -> game.id().equals(id))
            .findAny();
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public List<Game> getSummary() {
        List<Game> reversedGames = new ArrayList<>(this.games.reversed());
        reversedGames.sort(Comparator.comparingInt(Game::totalScore).reversed());
        return reversedGames;
    }
}
