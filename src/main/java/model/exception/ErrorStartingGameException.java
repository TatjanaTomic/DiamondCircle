package model.exception;

public class ErrorStartingGameException extends Exception{
    public ErrorStartingGameException() {
        super("There was an error while starting game.");
    }

    public ErrorStartingGameException(String message) {
        super(message);
    }
}
