package model.field;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import model.game.Game;

public abstract class Field extends StackPane {

    protected Rectangle rectangle;
    protected VBox content;
    private Label labelID;

    private int ID;
    private int numberOfRow;
    private int numberOfColumn;
    private Coordinates coordinates;

    protected Field(Coordinates coordinates, double width, double height) {
        this.coordinates = coordinates;
        numberOfRow = coordinates.getX(); //TODO: Provjeri ovo
        numberOfColumn = coordinates.getY();

        ID = coordinates.getX() * Game.dimension + coordinates.getY() + 1;

        rectangle = new Rectangle(width, height);

        content = new VBox();
        labelID = new Label(Integer.toString(ID));
        content.getChildren().addAll(labelID);

        getChildren().addAll(rectangle, content);
    }
}
