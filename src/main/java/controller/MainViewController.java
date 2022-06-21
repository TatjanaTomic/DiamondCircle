package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

    private static int c = 235;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfGamesLabel.setText(numberOfGamesText + Game.numberOfGames);

        initializeMap();


    }

    private void initializeMap() {
        for(int i=0; i < numberOfFields; i++) {
            for(int j = 0; j < numberOfFields; j++) {
                Field field;
                if(i==0 || i==2 || i==4 || i==6 || i ==8) {
                    field = new EmptyField("", i, j, fieldWidth, fieldHeight);
                } else {
                    field = new GameField("T", i, j, fieldWidth, fieldHeight, Color.rgb(c,c,c));
                }

                mapGridPane.add(field, i, j);
                c -= 1;
            }
        }
    }
}
