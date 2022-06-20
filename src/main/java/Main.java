import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import model.util.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Main {
    private static final Path configPath = Path.of("config.properties");

    public static int numberOfPlayers;
    public static int dimension;
    public static int n;

    public static void main(String[] args) {

        try {
            Properties configProperties = Util.LoadResources(configPath);
            checkConfigProperties(configProperties);

            DiamondCircleApplication.main(args);
        }
        catch (Exception e) {
            Util.log(Main.class, e);
        }
    }

    private static void checkConfigProperties(Properties configProperties) throws WrongConfigurationDefinitionException {

        try {
            numberOfPlayers = Integer.parseInt(configProperties.getProperty("numberOfPlayers"));
            dimension = Integer.parseInt(configProperties.getProperty("dimension"));
            n = Integer.parseInt(configProperties.getProperty("n"));
        }
        catch (Exception e) {
            throw new WrongConfigurationDefinitionException();
        }

        if(numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new WrongConfigurationDefinitionException("Number of players can be 2, 3 or 4!");
        }

        if(dimension < 7 || dimension > 10) {
            throw new WrongConfigurationDefinitionException("Dimension of matrix can be 7, 8, 9 or 10!");
        }

        //TODO : Provjeri koliko može biti n
        if(n < 7 || n > 10) {
            throw new WrongConfigurationDefinitionException("N can be 7, 8, 9 or 10!");
        }


    }
}










