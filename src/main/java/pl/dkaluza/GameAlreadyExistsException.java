package pl.dkaluza;

public class GameAlreadyExistsException extends ScoreboardException {
    public GameAlreadyExistsException(String message) {
        super(message);
    }

    public GameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
