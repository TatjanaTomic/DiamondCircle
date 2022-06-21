package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.exception.ErrorStartingGameException;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.game.Game;
import model.game.Simulation;
import model.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final String numberOfGamesText = "Trenutni broj odigranih igara: ";

    private final double mapWidth = 500;
    private final double mapHeight = 500;
    private final int numberOfFields = Game.dimension;
    private final double fieldWidth = (mapWidth / numberOfFields);
    private final double fieldHeight = (mapHeight / numberOfFields);

    private int redComponent = 215;
    private int greenComponent = 195;
    private int blueComponent = 215;
    private final Color startFieldColor = Color.WHITESMOKE;
    private final Color endFieldColor = Color.rgb(100,15,115);

    private static Simulation simulation = null;

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
        Field startField = new GameField("T", startCoordinates , fieldWidth, fieldHeight, startFieldColor, true, false);
        mapGridPane.add(startField, startCoordinates.getY(), startCoordinates.getX());

        for(int i = 1; i < Game.gamePath.size(); i++) {
            Coordinates coordinates = Game.gamePath.get(i);
            Field field = new GameField("Test", coordinates, fieldWidth, fieldHeight, Color.rgb(redComponent, greenComponent, blueComponent));
            mapGridPane.add(field, coordinates.getY(), coordinates.getX());
            redComponent -= 3;
            greenComponent -= 5;
            blueComponent -= 3;
        }

        Coordinates endCoordinates = Game.gamePath.get(Game.gamePath.size() - 1);
        Field endField = new GameField("T", endCoordinates, fieldWidth, fieldHeight, endFieldColor, false, true);
        mapGridPane.add(endField, endCoordinates.getY(), endCoordinates.getX());


        for(Coordinates coordinates : Game.emptyPath) {
            Field field = new EmptyField("", coordinates, fieldWidth, fieldHeight);
            mapGridPane.add(field, coordinates.getY(), coordinates.getX());
        }

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
}
