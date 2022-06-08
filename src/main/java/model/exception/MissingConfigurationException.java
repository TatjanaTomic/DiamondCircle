package model.exception;

public class MissingConfigurationException extends Exception {
    public MissingConfigurationException(){
        super("Configuration file does not exist.");
    }

    public MissingConfigurationException(String message) {
        super(message);
    }
}
