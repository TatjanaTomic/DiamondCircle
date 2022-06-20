package model.game;

import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import model.util.Util;

import java.nio.file.Path;
import java.util.*;

public class Game {
    private static final Path configPath = Path.of("config.properties");
    private static final Path playersPath = Path.of("players.properties");

    public static int numberOfPlayers;
    public static int dimension;
    public static int n;

    public static int numberOfGames = 0;

    public static List<String> players = new ArrayList<String>();

    public static void main(String[] args) {

        try {
            Properties configProperties = Util.LoadResources(configPath);
            checkConfigProperties(configProperties);

            readPlayers();

            GameSimulation simulation = new GameSimulation();

            DiamondCircleApplication.main(args);
        }
        catch (Exception e) {
            Util.log(Game.class, e);
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

    private static void readPlayers() throws MissingConfigurationException, WrongConfigurationDefinitionException {
        Properties playersProperties = Util.LoadResources(playersPath);

        Set<String> playerNames = new HashSet<String>();

        //TODO : Ako je u fajlu upisano vise imena nego sto je broj igraca u config fajlu, taj "visak" se ignorise, da li je to okej ?
        for(int i = 1; i <= numberOfPlayers; i++) {
            String name = playersProperties.getProperty("player" + i);
            if(name == null)
                throw new WrongConfigurationDefinitionException("Players names are not well formatted!");

            playerNames.add(name);
        }

        if(playerNames.size() < numberOfPlayers)
            throw new WrongConfigurationDefinitionException("Players names must be unique!");

        for(Object playerName : playerNames)
            players.add(playerName.toString());
    }

}










