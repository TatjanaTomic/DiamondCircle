package model.field;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import model.game.Game;

public abstract class Field extends AnchorPane {

    protected Rectangle rectangle;
    private Label labelID;
    protected Label contentLabel;

    private final int ID; //ID at whole map
    private final int pathID; //ID at path for playing
    private final int numberOfRow;
    private final int numberOfColumn;
    private final Coordinates coordinates;

    protected Field(int pathID, String fieldContent, Coordinates coordinates, double width, double height) {
        this.pathID = pathID;
        this.coordinates = coordinates;
        numberOfRow = coordinates.getX(); //TODO: Provjeri ovo
        numberOfColumn = coordinates.getY();

        ID = coordinates.getX() * Game.dimension + coordinates.getY() + 1;

        rectangle = new Rectangle(width, height);
        labelID = new Label(Integer.toString(ID));
        contentLabel = new Label(fieldContent);
        getChildren().addAll(rectangle, labelID, contentLabel);
        setTopAnchor(labelID, 1.0);
        setLeftAnchor(labelID, 1.0);
        setBottomAnchor(contentLabel, 0.0);
    }

    public int getID() {
        return ID;
    }

    public int getPathID() {
        return pathID;
    }

    public Label getContentLabel() {
        return contentLabel;
    }

}
