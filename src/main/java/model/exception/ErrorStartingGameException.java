package model.exception;

public class ErrorStartingGameException extends Exception{

    private static final String ERROR_MESSAGE = "There was an error while starting game.";

    public ErrorStartingGameException() {
        super(ERROR_MESSAGE);
    }

    public ErrorStartingGameException(String message) {
        super(message);
    }
}
