package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.figure.Figure;
import model.figure.FigureColor;
import model.game.Game;
import model.game.Simulation;
import model.player.Player;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final String NUMBER_OF_GAMES_TEXT = "Trenutni broj odigranih igara: ";
    private static final String TIME_LABEL_TEXT = "Vrijeme trajanja igre: ";
    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String DIAMOND_IMAGE = "diamond2.png";
    private static final String INITIAL_TIME = "0s";
    private static final String CURRENT_PLAYER = "Na potezu je igrac: ";
    private static final String CURRENT_FIGURE = "Trenutna figura: ";
    private static final String SPECIAL_CARD = "Specijalna karta - postavalju se rupe";
    private static final String TRANSITION_FROM = "Prelazak sa polja ";
    private static final String TRANSITION_TO = " na polje ";
    private static final String ARRIVAL = "Dolazak";
    private static final String NUMBER_OF_FIELDS = "Broj polja koja prelazi: ";
    private final double mapWidth = 550.0;
    private final double mapHeight = 550.0;
    private static final int numberOfFields = Game.dimension;
    private final double fieldWidth = mapWidth / (double) numberOfFields;
    private final double fieldHeight = mapHeight / (double) numberOfFields;

    private int redComponent = 215;
    private int greenComponent = 195;
    private int blueComponent = 215;
    private final Color startFieldColor = Color.WHITESMOKE;
    private final Color endFieldColor = Color.rgb(100,15,115);

    public static final Field[][] map = new Field[numberOfFields][numberOfFields];

    @FXML
    private Label numberOfGamesLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Label player3Label;
    @FXML
    private Label player4Label;
    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button startStopButton;

    @FXML
    private ListView<Figure> figuresList;

    @FXML
    private Pane mapPane;
    @FXML
    private GridPane mapGridPane;

    @FXML
    private ImageView cardImageView;

    // TODO : Uradi refresh izgleda !!!

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(Game.simulation == null)
            return;

        numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames);
        timeLabel.setText(TIME_LABEL_TEXT + INITIAL_TIME);
        cardImageView.setImage(new Image(new File(IMAGES_PATH + DIAMOND_IMAGE).toURI().toString()));
        descriptionTextArea.setWrapText(true);

        initializePlayersLabels();
        initializeMap();
        initializeFiguresList();
    }

    private void initializeMap() {

        Coordinates startCoordinates = Game.gamePath.get(0);
        GameField startField = new GameField(startCoordinates , fieldWidth, fieldHeight, startFieldColor, true, false);
        addField(startField, startCoordinates.getX(), startCoordinates.getY());

        for(int i = 1; i < Game.gamePath.size() - 1; i++) {
            Coordinates coordinates = Game.gamePath.get(i);
            GameField field = new GameField(coordinates, fieldWidth, fieldHeight, Color.rgb(redComponent, greenComponent, blueComponent));
            addField(field, coordinates.getX(), coordinates.getY());
            changeColor();
        }

        Coordinates endCoordinates = Game.gamePath.get(Game.gamePath.size() - 1);
        GameField endField = new GameField(endCoordinates, fieldWidth, fieldHeight, endFieldColor, false, true);
        addField(endField, endCoordinates.getX(), endCoordinates.getY());

        for(Coordinates coordinates : Game.emptyPath) {
            EmptyField field = new EmptyField(coordinates, fieldWidth, fieldHeight);
            addField(field, coordinates.getX(), coordinates.getY());
        }
    }

    private void addField(Field field, int x, int y) {
        mapGridPane.add(field, y, x); // add(Node node, int columnNumber, int rowNumber);
        map[x][y] = field;
    }

    private void initializePlayersLabels() {

        Label[] playersLabels = {player1Label, player2Label, player3Label, player4Label};

        for(int i = 1; i <= Game.numberOfPlayers; i++) {
            String oldText = playersLabels[i-1].getText();
            playersLabels[i-1].setText(oldText + Game.simulation.getPlayers().get(i-1));
            playersLabels[i-1].setTextFill(Paint.valueOf(Game.simulation.getPlayers().get(i-1).getColor().toString()));
        }
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
        Game.StartResumeGame();
        startStopButton.setDisable(true);
    }

    public void enableButton() {
        startStopButton.setDisable(false);
    }

    public static GameField getFieldByPathID(int fieldPathID) throws IllegalStateOfGameException {

        if(fieldPathID < 1 || fieldPathID > Game.gamePath.size())
            throw new IllegalStateOfGameException("Illegal value of field path ID!");

        for(int i = 0; i < numberOfFields; i++) {
            for(int j = 0; j < numberOfFields; j++ ) {
                if(map[i][j].getPathID() == fieldPathID) {
                    return (GameField) map[i][j];
                }
            }
        }

        return null;
    }

    private void initializeFiguresList() {

        ObservableList<Figure> figures = FXCollections.observableArrayList();
        for (Player p : Game.simulation.getPlayers()) {
            figures.addAll(p.getFigures());
        }
        figuresList.setItems(figures);

        figuresList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Figure item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    ImageView imageView = new ImageView(new Image(new File(IMAGES_PATH + item.getImageName()).toURI().toString()));
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    setGraphic(imageView);

                    setText(item.toString());
                    setTextFill(Paint.valueOf(item.getColor().toString()));

                    setOnMousePressed(e -> itemClickedTest());
                }
            }
        });

        figuresList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void itemClickedTest() {
        StringBuilder message = new StringBuilder();
        ObservableList<Figure> figures = figuresList.getSelectionModel().getSelectedItems();

        for(Figure f : figures) {
            message.append(f.getClass().getSimpleName()).append(" ").append(f.getPlayerName());
        }
        //TODO : Odavde treba otvarati novi prozor za figuru
        System.out.println(message);
    }

    public void setCard(String cardName) {
        Platform.runLater(() -> cardImageView.setImage(new Image
                (new File(IMAGES_PATH + cardName).toURI().toString())));
    }

    public void setTime(int timeInSeconds) {
        Platform.runLater(() -> timeLabel.setText(TIME_LABEL_TEXT + timeInSeconds + "s"));
    }

    public void updateNumberOfGames() {
        Platform.runLater(() -> numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames));
    }

    public void setDescription(boolean isSpecialCard) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CURRENT_PLAYER);

        if(isSpecialCard) {
            stringBuilder.append(Game.simulation.getCurrentPlayer()).append("\n");
            stringBuilder.append(SPECIAL_CARD);
        }
        else {
            stringBuilder.append(Game.simulation.getCurrentPlayer()).append("\n");
            stringBuilder.append(CURRENT_FIGURE);
            stringBuilder.append(Game.simulation.getCurrentPlayer().getCurrentFigure()).append("\n");
            stringBuilder.append(NUMBER_OF_FIELDS);
            stringBuilder.append(Game.simulation.getCurrentPlayer().getCurrentFigure().getNumberOfFields()).append("\n");
            if(Game.simulation.getCurrentPlayer().getCurrentFigure().getCurrentField() == null) {
                stringBuilder.append(ARRIVAL);
            }
            else {
                stringBuilder.append(TRANSITION_FROM);
                stringBuilder.append(Game.simulation.getCurrentPlayer().getCurrentFigure().getCurrentField());
            }
            stringBuilder.append(TRANSITION_TO);
            stringBuilder.append(Game.simulation.getCurrentPlayer().getCurrentFigure().getNextField()).append("\n");
        }

        Platform.runLater(() -> descriptionTextArea.setText(stringBuilder.toString()));
    }

}
