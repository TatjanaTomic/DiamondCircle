package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.game.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final String numberOfGamesText = "Trenutni broj odigranih igara: ";


    @FXML
    public Label numberOfGamesLabel;

    @FXML
    public Button startStopButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfGamesLabel.setText(numberOfGamesText + Game.numberOfGames);
    }
}
