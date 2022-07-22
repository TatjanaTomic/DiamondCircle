package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import model.field.Coordinates;
import model.figure.Figure;
import model.game.Game;
import model.util.LoggerUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class FigureController implements Initializable {

    public static Figure figure;
    private static final String PURPLE_COLOR = "#C45499";
    private static final String LIGHT_GRAY_COLOR = "LightGray";
    private static final String DARK_GRAY_COLOR = "DarkGray";
    @FXML
    private GridPane figurePathGridPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(figure == null)
            return;

        for(Coordinates c : Game.gamePath) {
            int fieldID = c.getX() * Game.dimension + c.getY() + 1;
            AnchorPane field;

            if(figure.getCrossedFields().contains(fieldID)) {
                field = makeField(Integer.toString(fieldID), PURPLE_COLOR);
            }
            else {
                field = makeField(Integer.toString(fieldID), DARK_GRAY_COLOR);
            }
            figurePathGridPane.add(field, c.getY(), c.getX());
        }

        for(Coordinates c : Game.emptyPath) {
            int fieldID = c.getX() * Game.dimension + c.getY() + 1;
            AnchorPane field = makeField(Integer.toString(fieldID), LIGHT_GRAY_COLOR);
            figurePathGridPane.add(field, c.getY(), c.getX());
        }
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
