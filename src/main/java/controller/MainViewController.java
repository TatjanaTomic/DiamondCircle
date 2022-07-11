package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.game.Game;
import model.game.Simulation;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final String NUMBER_OF_GAMES_TEXT = "Trenutni broj odigranih igara: ";
    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String DIAMOND_IMAGE = "diamond2.png";

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
    public static final Field[][] map = new Field[numberOfFields][numberOfFields];

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

    @FXML
    public ImageView cardImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(simulation == null)
            return;

        numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames);
        cardImageView.setImage(new Image(new File(IMAGES_PATH + DIAMOND_IMAGE).toURI().toString()));

        initializePlayersLabels();
        initializeMap();
    }

    private void initializeMap() {

        int id = 1;
        Coordinates startCoordinates = Game.gamePath.get(0);
        GameField startField = new GameField(id++, "START", startCoordinates , fieldWidth, fieldHeight, startFieldColor, true, false);
        addField(startField, startCoordinates.getX(), startCoordinates.getY());

        for(int i = 1; i < Game.gamePath.size(); i++) {
            Coordinates coordinates = Game.gamePath.get(i);
            GameField field = new GameField(id++,"Test", coordinates, fieldWidth, fieldHeight, Color.rgb(redComponent, greenComponent, blueComponent));
            addField(field, coordinates.getX(), coordinates.getY());
            changeColor();
        }

        Coordinates endCoordinates = Game.gamePath.get(Game.gamePath.size() - 1);
        GameField endField = new GameField(id, "END", endCoordinates, fieldWidth, fieldHeight, endFieldColor, false, true);
        addField(endField, endCoordinates.getX(), endCoordinates.getY());

        for(Coordinates coordinates : Game.emptyPath) {
            EmptyField field = new EmptyField(coordinates, fieldWidth, fieldHeight);
            addField(field, coordinates.getX(), coordinates.getY());
        }
    }

    private void addField(Field field, int x, int y) {

        mapGridPane.getChildren().remove(map[x][y]);
        mapGridPane.add(field, y, x); // add(Node node, int columnNumber, int rowNumber);
        field.getTextProperty().addListener((observableValue, s, t1) -> updateMapGridPane());
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

        //GhostFigure ghostFigure = new GhostFigure();
        //ghostFigure.start();
        simulation.start();
    }

    public static Field getFieldByPathID(int fieldPathID) throws IllegalStateOfGameException {

        if(fieldPathID < 1 || fieldPathID > Game.gamePath.size())
            throw new IllegalStateOfGameException("Illegal value of field path ID!");

        for(int i = 0; i < numberOfFields; i++) {
            for(int j = 1; j < numberOfFields; j++ ) {
                if(map[i][j].getPathID() == fieldPathID) {
                    return map[i][j];
                }
            }
        }

        return null;
    }

    private void updateMapGridPane() {

        for(int i = 0; i < numberOfFields; i++) {
            for(int j = 0; j < numberOfFields; j++) {
                if(map[i][j] != null) {
                    mapGridPane.getChildren().remove(map[i][j]);
                    mapGridPane.add(map[i][j], j, i); // add(Node node, int columnNumber, int rowNumber);
                }
            }
        }
    }

    public void setCard(String cardName) {
        Platform.runLater(() -> cardImageView.setImage(new Image
                (new File(IMAGES_PATH + cardName).toURI().toString())));
    }


}
