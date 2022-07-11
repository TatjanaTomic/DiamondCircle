package model.exception;

public class IllegalStateOfGameException extends Exception{

    private static final String ERROR_MESSAGE = "En error while playing game occurred!";

    public IllegalStateOfGameException() {
        super(ERROR_MESSAGE);
    }

    public IllegalStateOfGameException(String message) {
        super(message);
    }
}
