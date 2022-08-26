package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.EmptyField;
import model.field.Field;
import model.field.GameField;
import model.figure.Figure;
import model.game.Game;
import model.player.Player;
import model.util.LoggerUtil;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String DIAMOND_IMAGE = "diamond2.png";
    private static final String HISTORY_FXML = "./view/HistoryView.fxml";
    private static final String FIGURE_FXML = "view/FigurePathView.fxml";
    private static final String NUMBER_OF_GAMES_TEXT = "Trenutni broj odigranih igara: ";
    private static final String TIME_LABEL_TEXT = "Vrijeme trajanja igre: ";
    private static final String INITIAL_TIME = "0s";
    private static final String SECONDS = "s";
    private static final String CURRENT_PLAYER = "Na potezu je igrac: ";
    private static final String CURRENT_FIGURE = "Trenutna figura: ";
    private static final String SPECIAL_CARD = "Specijalna karta - postavalju se rupe";
    private static final String TRANSITION_FROM = "Prelazak sa polja ";
    private static final String TRANSITION_TO = " na polje ";
    private static final String ARRIVAL = "Dolazak";
    private static final String NUMBER_OF_FIELDS = "Broj polja koja prelazi: ";
    private static final String GAME_FINISHED = "Igra je zavrsena!";
    private static final String FIRST_PLAYER = "Prvi igrac: ";
    private static final String SECOND_PLAYER = "Drugi igrac: ";
    private static final String THIRD_PLAYER = "Treci igrac: ";
    private static final String FOURTH_PLAYER = "Cetvrti igrac: ";
    private static final String HISTORY_TITLE = "Rezultati";
    private static final String FIGURE_TITLE = "Predjeni put figure ";
    private static final String ID_ERROR_MESSAGE = "Illegal value of field path ID!";
    private static final String NEW_GAME = "Nova igra";
    private static final String PAUSE = "Pauza";
    private static final String RESUME = "Nastavak";

    private static final int numberOfFields = Game.dimension;

    private static final double mapWidth = 550.0;
    private static final double mapHeight = 550.0;
    static final double fieldWidth = mapWidth / numberOfFields;
    static final double fieldHeight = mapHeight / numberOfFields;

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
    private GridPane mapGridPane;

    @FXML
    private ImageView cardImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(Game.simulation == null)
            return;

        numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames);
        cardImageView.setImage(new Image(new File(IMAGES_PATH + DIAMOND_IMAGE).toURI().toString()));
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setEditable(false);

        updateView();
        initializeMap();
    }

    public void updateView() {
        timeLabel.setText(TIME_LABEL_TEXT + INITIAL_TIME);
        timeLabel.getStyleClass().remove("purple-border-style");

        updateButtonText();

        initializePlayersLabels();
        initializeFiguresList();
    }

    public void resetView() {
        cardImageView.setImage(new Image(new File(IMAGES_PATH + DIAMOND_IMAGE).toURI().toString()));
        descriptionTextArea.setText(GAME_FINISHED);
        timeLabel.getStyleClass().add("purple-border-style");

        updateButtonText();

        for(int i = 0; i < numberOfFields; i++) {
            for(int j = 0; j < numberOfFields; j++ ) {
                if(map[i][j] instanceof GameField) {
                    ((GameField) map[i][j]).setDiamondAdded(false);
                }
            }
        }

        Label[] playersLabels = {player1Label, player2Label, player3Label, player4Label};
        for(int i = 0; i < Game.numberOfPlayers; i++) {
            String basicStyle = "-fx-border-color: #e5ceed;";
            playersLabels[i].setStyle(basicStyle);
        }

        startStopButton.setDisable(false);
    }

    private void initializeMap() {

        Coordinates startCoordinates = Game.gamePath.get(0);
        GameField startField = new GameField(startCoordinates , fieldWidth, fieldHeight, true, false);
        addField(startField, startCoordinates.getX(), startCoordinates.getY());

        for(int i = 1; i < Game.gamePath.size() - 1; i++) {
            Coordinates coordinates = Game.gamePath.get(i);
            GameField field = new GameField(coordinates, fieldWidth, fieldHeight);
            addField(field, coordinates.getX(), coordinates.getY());
        }

        Coordinates endCoordinates = Game.gamePath.get(Game.gamePath.size() - 1);
        GameField endField = new GameField(endCoordinates, fieldWidth, fieldHeight, false, true);
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
        player1Label.setText(FIRST_PLAYER);
        player2Label.setText(SECOND_PLAYER);
        player3Label.setText(THIRD_PLAYER);
        player4Label.setText(FOURTH_PLAYER);

        for(int i = 0; i < Game.numberOfPlayers; i++) {
            String initialText = playersLabels[i].getText();
            playersLabels[i].setText(initialText + Game.simulation.getPlayers().get(i));
            playersLabels[i].setTextFill(Paint.valueOf(Game.simulation.getPlayers().get(i).getColor().toString()));
        }
    }

    public void startStopGame() {
        Game.StartResumeGame();
    }

    public static GameField getFieldByPathID(int fieldPathID) throws IllegalStateOfGameException {

        if(fieldPathID < 1 || fieldPathID > Game.gamePath.size())
            throw new IllegalStateOfGameException(ID_ERROR_MESSAGE);

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
                    setBackground(Background.EMPTY);
                } else {
                    ImageView imageView = new ImageView(new Image(new File(IMAGES_PATH + item.getImageName()).toURI().toString()));
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    setGraphic(imageView);

                    setText(item.toString());
                    setTextFill(Paint.valueOf(item.getColor().toString()));

                    getStyleClass().add("list-item-style");

                    setOnMousePressed(e -> figureClicked());
                }
            }
        });

        figuresList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private Stage figureStage;

    private void figureClicked() {

        if(figureStage != null) {
            figureStage.close();
        }

        try {

            Figure selectedFigure = figuresList.getSelectionModel().getSelectedItems().get(0);
            FigurePathController.figure = selectedFigure;

            FXMLLoader loader = new FXMLLoader(MainViewController.class.getClassLoader().getResource(FIGURE_FXML));
            Parent root = loader.load();

            figureStage = new Stage();
            figureStage.setTitle(FIGURE_TITLE + selectedFigure.toString());
            figureStage.setScene(new Scene(root));
            figureStage.setResizable(false);
            figureStage.show();
        }
        catch (IOException e) {
            LoggerUtil.logAsync(getClass(), e);
        }

    }

    public void updateCard(String cardName) {
        Platform.runLater(() -> cardImageView.setImage(new Image
                (new File(IMAGES_PATH + cardName).toURI().toString())));
    }

    public void updateTime(int timeInSeconds) {
        Platform.runLater(() -> timeLabel.setText(TIME_LABEL_TEXT + timeInSeconds + SECONDS));
    }

    public void updateNumberOfGames() {
        Platform.runLater(() -> numberOfGamesLabel.setText(NUMBER_OF_GAMES_TEXT + Game.numberOfGames));
    }

    public void updateButtonText() {
        if(Game.finished || !Game.started) {
            Platform.runLater(() -> startStopButton.setText(NEW_GAME));
            return;
        }

        if(Game.paused) {
            Platform.runLater(() -> startStopButton.setText(RESUME));
        }
        else {
            Platform.runLater(() -> startStopButton.setText(PAUSE));
        }
    }

    public void updateCurrentPlayer() {
        Label[] playersLabels = {player1Label, player2Label, player3Label, player4Label};
        Player currentPlayer = Game.simulation.getCurrentPlayer();

        Platform.runLater(() -> {
            for(int i = 0; i < Game.numberOfPlayers; i++) {
                if(playersLabels[i].getText().contains(currentPlayer.getName())) {
                    String style = "-fx-border-color: " + Game.simulation.getCurrentPlayer().getColor() + ";";
                    playersLabels[i].setStyle(style);
                } else {
                    String basicStyle = "-fx-border-color: #e5ceed;";
                    playersLabels[i].setStyle(basicStyle);
                }
            }
        });
    }
    
    public void updateDescription(boolean isSpecialCard) {
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


    private Stage historyStage;

    @FXML
    private void showHistory() {

        if(historyStage != null) {
            historyStage.close();
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainViewController.class.getClassLoader().getResource(HISTORY_FXML));
            Parent root = loader.load();

            historyStage = new Stage();
            historyStage.setTitle(HISTORY_TITLE);
            historyStage.setScene(new Scene(root));
            historyStage.setMinWidth(600);
            historyStage.setMinHeight(400);
            historyStage.show();
        }
        catch (IOException e) {
            LoggerUtil.logAsync(getClass(), e);
        }

    }
}
