package pl.dkaluza;

public class TeamAlreadyPlaysException extends ScoreboardException{
    public TeamAlreadyPlaysException(String message) {
        super(message);
    }

    public TeamAlreadyPlaysException(String message, Throwable cause) {
        super(message, cause);
    }
}
