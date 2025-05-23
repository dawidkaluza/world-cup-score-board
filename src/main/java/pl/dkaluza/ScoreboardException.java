package pl.dkaluza;

public abstract class ScoreboardException extends RuntimeException {
    public ScoreboardException(String message) {
        super(message);
    }

    public ScoreboardException(String message, Throwable cause) {
        super(message, cause);
    }
}
