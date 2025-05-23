package pl.dkaluza;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
