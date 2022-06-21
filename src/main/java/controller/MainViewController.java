package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.game.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final String numberOfGamesText = "Trenutni broj odigranih igara: ";

    private static final double mapWidth = 400;
    private static final double mapHeight = 400;
    private static final int numberOfFields = Game.dimension;
    private static final double fieldWidth = (mapWidth / numberOfFields);
    private static final double fieldHeight = (mapHeight / numberOfFields);


    @FXML
    public Label numberOfGamesLabel;

    @FXML
    public Button startStopButton;

    @FXML
    public GridPane mapGridPane;

    private static int redComponent = 246 - (7*4);
    private static int greenComponent = 242 - (7*6);
    private static int blueComponent = 246 - (7*4);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfGamesLabel.setText(numberOfGamesText + Game.numberOfGames);

        initializeMap();


    }

    private void initializeMap() {
        for(Coordinates coordinates : Game.gamePath) {
            Field field = new GameField("T", coordinates.getX(), coordinates.getY(), fieldWidth, fieldHeight, Color.rgb(redComponent, greenComponent, blueComponent));
            mapGridPane.add(field, coordinates.getY(), coordinates.getX());
            redComponent -= 3;
            greenComponent -= 5;
            blueComponent -= 3;
        }
        for(Coordinates coordinates : Game.emptyPath) {
            Field field = new EmptyField("", coordinates.getX(), coordinates.getY(), fieldWidth, fieldHeight);
            mapGridPane.add(field, coordinates.getY(), coordinates.getX());
        }

    }


}
