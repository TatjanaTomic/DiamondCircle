package model.exception;

public class WrongConfigurationDefinitionException extends Exception{
    public WrongConfigurationDefinitionException(){
        super("Configuration properties are not well formatted.");
    }

    public WrongConfigurationDefinitionException(String message) {
        super(message);
    }
}
