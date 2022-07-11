package model.exception;

public class MissingConfigurationException extends Exception {

    private static final String ERROR_MESSAGE = "Configuration file does not exist.";

    public MissingConfigurationException(){
        super(ERROR_MESSAGE);
    }

    public MissingConfigurationException(String message) {
        super(message);
    }
}
