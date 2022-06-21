package model.field;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public abstract class Field extends StackPane {

    protected Rectangle rectangle;
    protected Label label;

    private int numberOfRow;
    private int numberOfColumn;
    private Coordinates coordinates;

    protected Field(String fieldContent, Coordinates coordinates, double width, double height) {
        this.coordinates = coordinates;
        numberOfRow = coordinates.getX(); //TODO: Provjeri ovo
        numberOfColumn = coordinates.getY();

        rectangle = new Rectangle(width, height);
        label = new Label(fieldContent);
        label.setStyle("-fx-background-color: #ffffff;");

        getChildren().addAll(rectangle, label);
    }
}
