package model.exception;

public class WrongConfigurationDefinitionException extends Exception{

    private static final String ERROR_MESSAGE = "Configuration properties are not well formatted.";

    public WrongConfigurationDefinitionException(){
        super(ERROR_MESSAGE);
    }

    public WrongConfigurationDefinitionException(String message) {
        super(message);
    }
}
