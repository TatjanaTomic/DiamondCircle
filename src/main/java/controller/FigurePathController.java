package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.field.Coordinates;
import model.figure.Figure;
import model.game.Game;
import model.util.LoggerUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class FigurePathController implements Initializable {

    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String REACHED_FIELD = "Predjeno polje";
    private static final String SKIPPED_FIELD = "Preskoceno (nedostignuto) polje";
    private static final String PURPLE_COLOR = "#640f73";
    private static final String LIGHT_GRAY_COLOR = "LightGray";
    private static final String DARK_GRAY_COLOR = "Gray";

    public static Figure figure;

    @FXML
    private ImageView figureImage;
    @FXML
    private Label figureLabel;
    @FXML
    private Label reachedFieldLabel;
    @FXML
    private Label skippedFieldLabel;
    @FXML
    private Rectangle reachedFieldRectangle;
    @FXML
    private Rectangle skippedFieldRectangle;
    @FXML
    private GridPane figurePathGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(figure == null)
            return;

        figureImage.setImage(new Image(new File(IMAGES_PATH + figure.getImageName()).toURI().toString()));
        figureImage.setFitHeight(50);
        figureImage.setFitWidth(50);

        figureLabel.setText(figure.toString());

        reachedFieldRectangle.setFill(Paint.valueOf(PURPLE_COLOR));
        reachedFieldLabel.setText(REACHED_FIELD);
        skippedFieldRectangle.setFill(Paint.valueOf(DARK_GRAY_COLOR));
        skippedFieldLabel.setText(SKIPPED_FIELD);

        for(Coordinates c : Game.gamePath) {
            int fieldID = c.getX() * Game.dimension + c.getY() + 1;
            AnchorPane field = makeField(Integer.toString(fieldID), DARK_GRAY_COLOR);
            field.setId(Integer.toString(fieldID));
            figurePathGridPane.add(field, c.getY(), c.getX());
        }

        for(Coordinates c : Game.emptyPath) {
            int fieldID = c.getX() * Game.dimension + c.getY() + 1;
            AnchorPane field = makeField(Integer.toString(fieldID), LIGHT_GRAY_COLOR);
            field.setId("-1");
            figurePathGridPane.add(field, c.getY(), c.getX());
        }

        paintReachedFields();
    }

    private void paintReachedFields() {
        new Thread(() -> {
            while(true) {

                for(Node f : figurePathGridPane.getChildren()) {
                    if (figure.getCrossedFields().contains(Integer.parseInt(f.getId()))) {
                        AnchorPane field = (AnchorPane) f;
                        Platform.runLater(() -> {
                            Rectangle rectangle = (Rectangle) field.getChildren().get(0);
                            rectangle.setFill(Paint.valueOf(PURPLE_COLOR));
                        });
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LoggerUtil.log(getClass(), e);
                }
            }
        }).start();
    }

    private AnchorPane makeField(String content, String color) {
        AnchorPane field = new AnchorPane();
        Rectangle rectangle = new Rectangle(MainViewController.fieldWidth, MainViewController.fieldHeight);
        rectangle.setFill(javafx.scene.paint.Paint.valueOf(color));
        Label labelID = new Label(content);

        field.getChildren().addAll(rectangle, labelID);
        setTopAnchor(labelID, 1.0);
        setLeftAnchor(labelID, 1.0);

        return field;
    }

}
