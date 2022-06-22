package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.figure.GhostFigure;
import model.game.Game;
import model.game.Simulation;
import model.util.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final String numberOfGamesText = "Trenutni broj odigranih igara: ";

    private final double mapWidth = 500;
    private final double mapHeight = 500;
    private static final int numberOfFields = Game.dimension;
    private final double fieldWidth = (mapWidth / numberOfFields);
    private final double fieldHeight = (mapHeight / numberOfFields);

    private int redComponent = 215;
    private int greenComponent = 195;
    private int blueComponent = 215;
    private final Color startFieldColor = Color.WHITESMOKE;
    private final Color endFieldColor = Color.rgb(100,15,115);

    //TODO : Da li je bolje da bude staticko ili ipak ne ?
    private static Simulation simulation = null;
    public static Field[][] map = new Field[numberOfFields][numberOfFields];

    @FXML
    public Label numberOfGamesLabel;
    @FXML
    public Label player1Label;
    @FXML
    public Label player2Label;
    @FXML
    public Label player3Label;
    @FXML
    public Label player4Label;

    @FXML
    public Button startStopButton;

    @FXML
    public GridPane mapGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(simulation == null)
            return;

        numberOfGamesLabel.setText(numberOfGamesText + Game.numberOfGames);

        initializePlayersLabels();
        initializeMap();

    }

    private void initializeMap() {

        Coordinates startCoordinates = Game.gamePath.get(0);
        GameField startField = new GameField("START", startCoordinates , fieldWidth, fieldHeight, startFieldColor, true, false);
        addField(startField, startCoordinates.getX(), startCoordinates.getY());
        startField.setDiamondAdded(true);

        for(int i = 1; i < Game.gamePath.size(); i++) {
            Coordinates coordinates = Game.gamePath.get(i);
            GameField field = new GameField("Test", coordinates, fieldWidth, fieldHeight, Color.rgb(redComponent, greenComponent, blueComponent));
            addField(field, coordinates.getX(), coordinates.getY());
            changeColor();
        }

        Coordinates endCoordinates = Game.gamePath.get(Game.gamePath.size() - 1);
        GameField endField = new GameField("END", endCoordinates, fieldWidth, fieldHeight, endFieldColor, false, true);
        addField(endField, endCoordinates.getX(), endCoordinates.getY());

        for(Coordinates coordinates : Game.emptyPath) {
            EmptyField field = new EmptyField(coordinates, fieldWidth, fieldHeight);
            addField(field, coordinates.getX(), coordinates.getY());
        }
    }

    private void addField(Field field, int x, int y) {

        mapGridPane.getChildren().remove(map[x][y]);
        mapGridPane.add(field, y, x); // add(Node node, int columnNumber, int rowNumber);
        //field.getTextProperty().addListener((observableValue, s, t1) -> updateGridPane());
        map[x][y] = field;
    }

    private void initializePlayersLabels() {

        Label[] playersLabels = {player1Label, player2Label, player3Label, player4Label};

        for(int i = 1; i <= Game.numberOfPlayers; i++) {
            playersLabels[i-1].setText("Igrač " + i +": " + simulation.getPlayers().get(i-1).getName());
            playersLabels[i-1].setTextFill(Paint.valueOf(simulation.getPlayers().get(i-1).getColor().toString()));
        }
    }

    public static void setSimulation(Simulation sim) {
        simulation = sim;
    }

    private void changeColor() {
        if(numberOfFields == 7) {
            redComponent -= 3;
            greenComponent -= 5;
            blueComponent -= 3;
        }
        else if(numberOfFields == 8 || numberOfFields == 9) {
            redComponent -= 2;
            greenComponent -= 4;
            blueComponent -= 2;
        }
        else if(numberOfFields == 10) {
            redComponent -= 1;
            greenComponent -= 3;
            blueComponent -= 1;
        }
    }

    public void test() {
        simulation.startSimulation();
    }
}
