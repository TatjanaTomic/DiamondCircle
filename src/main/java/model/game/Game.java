package model.game;

import controller.MainViewController;
import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import model.field.Coordinates;
import model.figure.Figure;
import model.player.Player;
import model.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.file.Path;
import java.util.*;

public class Game {
    private static final Path configPath = Path.of("config.properties");
    private static final Path playersPath = Path.of("players.properties");
    private static final Path matrixConfigs = Path.of("src/main/resources/view/matrixConfigs/");

    public static int numberOfPlayers;
    public static int dimension;
    public static int n;

    public static List<Coordinates> gamePath = new ArrayList<>();
    public static List<Coordinates> emptyPath = new ArrayList<>();

    public static int numberOfGames = 0;

    public static List<String> players = new ArrayList<>();

    public static void main(String[] args) {

        try {
            checkConfigProperties();
            readPlayers();
            loadMatrixConfiguration();

            Simulation simulation = SimulationBuilder.build();

            List<Player> test = simulation.getPlayers();
            for (Player p : test) {
                System.out.println("Igrac: " + p.getName() + " Boja: " + p.getColor());
                for (Figure f: p.getFigures()) {
                    System.out.println("    " + f.getClass().getSimpleName());
                }

            }

            MainViewController.setSimulation(simulation);
            DiamondCircleApplication.main(args);
        }
        catch (Exception e) {
            Util.log(Game.class, e);
        }
    }

    private static void checkConfigProperties() throws WrongConfigurationDefinitionException, MissingConfigurationException {

        Properties configProperties = Util.LoadResources(configPath);

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

        Set<String> playersNames = new HashSet<>();

        //TODO : Ako je u fajlu upisano vise imena nego sto je broj igraca u config fajlu, taj "visak" se ignorise, da li je to okej ?
        for(int i = 1; i <= numberOfPlayers; i++) {
            String name = playersProperties.getProperty("player" + i);
            if(name == null)
                throw new WrongConfigurationDefinitionException("Players names are not well formatted!");

            playersNames.add(name);
        }

        if(playersNames.size() < numberOfPlayers)
            throw new WrongConfigurationDefinitionException("Players names must be unique!");

        for(Object playerName : playersNames)
            players.add(0, playerName.toString());
    }

    private static void loadMatrixConfiguration() throws MissingConfigurationException, WrongConfigurationDefinitionException{

        JSONArray gamePathJson = Util.ReadMatrixConfiguration(matrixConfigs.resolve("matrix" + dimension + "gamePath.json"));
        JSONArray emptyPathJson = Util.ReadMatrixConfiguration(matrixConfigs.resolve("matrix" + dimension + "emptyPath.json"));

        for (Object object: gamePathJson)
            gamePath.add(parseCoordinates((JSONObject) object));

        for (Object object: emptyPathJson)
            emptyPath.add(parseCoordinates((JSONObject) object));
    }

    private static Coordinates parseCoordinates(JSONObject jsonObject) {
        int x = ((Long) jsonObject.get("x")).intValue();
        int y = ((Long) jsonObject.get("y")).intValue();

        return new Coordinates(x,y);
    }
}










