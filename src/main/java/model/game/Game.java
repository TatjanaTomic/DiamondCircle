package model.game;

import controller.MainViewController;
import model.exception.ErrorStartingGameException;
import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import model.field.Coordinates;
import model.field.Field;
import model.figure.Figure;
import model.figure.GhostFigure;
import model.player.Player;
import model.util.ConfigUtil;
import model.util.LoggerUtil;
import model.util.TimeCounter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.file.Path;
import java.util.*;

public class Game {
    private static final Path configPath = Path.of("config.properties");
    private static final Path playersPath = Path.of("players.properties");
    private static final Path matrixConfigs = Path.of("src/main/resources/view/matrixConfigs/");

    private static final String DIM_ERROR_MESSAGE = "Dimension of matrix can be 7, 8, 9 or 10!";
    private static final String PLAYERS_ERROR_MESSAGE = "Number of players can be 2, 3 or 4!";
    private static final String NAMES_FORMAT_ERROR_MESSAGE = "Players names are not well formatted!";
    private static final String NAMES_UNIQUE_ERROR_MESSAGE = "Players names must be unique!";

    public static int dimension;
    public static int numberOfPlayers;
    public static int numberOfGames = 0;
    public static List<Coordinates> gamePath = new ArrayList<>();
    public static List<Coordinates> emptyPath = new ArrayList<>();
    public static List<String> playersNames = new ArrayList<>();

    public static TimeCounter timeCounter;
    private static GhostFigure ghostFigure;

    public static Simulation simulation = null;

    private static Thread timeCounterThread;
    private static Thread ghostFigureThread;
    private static Thread gameThread;

    public static void main(String[] args) {

        try {
            LoggerUtil.createLogsDirectory();

            checkConfigProperties();
            readPlayers();
            loadMatrixConfiguration();

            simulation = SimulationBuilder.build();

            DiamondCircleApplication.main(args);
        }
        catch (Exception e) {
            LoggerUtil.log(Game.class, e);
        }
    }

    public static void StartResumeGame() {

        if(simulation == null) {
            try {
                simulation = SimulationBuilder.build();
                DiamondCircleApplication.mainController.updateView();
            }
            catch (ErrorStartingGameException e) {
                LoggerUtil.log(Game.class, e);
                return;
            }
        }
        timeCounter = new TimeCounter();
        ghostFigure = new GhostFigure();

        timeCounterThread = new Thread(timeCounter);
        ghostFigureThread = new Thread(ghostFigure);
        gameThread = new Thread(simulation);

        timeCounterThread.start();
        ghostFigureThread.start();
        gameThread.start();
    }

    public static void finishGame() {
        simulation.stop();
        ghostFigure.stop();
        timeCounter.stop();
        simulation = null;
        ghostFigure = null;
        timeCounter = null;
    }

    private static void checkConfigProperties() throws WrongConfigurationDefinitionException, MissingConfigurationException {

        Properties configProperties = ConfigUtil.LoadResources(configPath);

        try {
            numberOfPlayers = Integer.parseInt(configProperties.getProperty("numberOfPlayers"));
            dimension = Integer.parseInt(configProperties.getProperty("dimension"));
        }
        catch (Exception e) {
            throw new WrongConfigurationDefinitionException();
        }

        if(numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new WrongConfigurationDefinitionException(PLAYERS_ERROR_MESSAGE);
        }

        if(dimension < 7 || dimension > 10) {
            throw new WrongConfigurationDefinitionException(DIM_ERROR_MESSAGE);
        }

    }

    private static void readPlayers() throws MissingConfigurationException, WrongConfigurationDefinitionException {

        Properties playersProperties = ConfigUtil.LoadResources(playersPath);

        List<String> playersNames = new ArrayList<>();

        //TODO : Ako je u fajlu upisano vise imena nego sto je broj igraca u config fajlu, taj "visak" se ignorise, da li je to okej ?
        for(int i = 1; i <= numberOfPlayers; i++) {
            String name = playersProperties.getProperty("player" + i);
            if(name == null)
                throw new WrongConfigurationDefinitionException(NAMES_FORMAT_ERROR_MESSAGE);

            playersNames.add(name);
        }

        if(playersNames.stream().distinct().count() < playersNames.size())
            throw new WrongConfigurationDefinitionException(NAMES_UNIQUE_ERROR_MESSAGE);

        Game.playersNames.addAll(playersNames);
    }

    private static void loadMatrixConfiguration() throws MissingConfigurationException, WrongConfigurationDefinitionException{

        JSONArray gamePathJson = ConfigUtil.ReadMatrixConfiguration(matrixConfigs.resolve("matrix" + dimension + "gamePath.json"));
        JSONArray emptyPathJson = ConfigUtil.ReadMatrixConfiguration(matrixConfigs.resolve("matrix" + dimension + "emptyPath.json"));

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










