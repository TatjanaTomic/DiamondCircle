package model.exception;

public class IllegalStateOfGameException extends Exception{
    public IllegalStateOfGameException() {
        super("En error while playing game occured!");
    }

    public IllegalStateOfGameException(String message) {
        super(message);
    }
}
