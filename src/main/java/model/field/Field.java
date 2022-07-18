package model.field;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import model.game.Game;

public abstract class Field extends AnchorPane {

    protected Rectangle rectangle;

    protected final int ID; //ID at whole map
    protected final int pathID; //ID at path for playing
    private final Coordinates coordinates;

    protected Field(int pathID, Coordinates coordinates, double width, double height) {
        ID = coordinates.getX() * Game.dimension + coordinates.getY() + 1;
        this.pathID = pathID;
        this.coordinates = coordinates;

        rectangle = new Rectangle(width, height);
        Label labelID = new Label(Integer.toString(ID));

        getChildren().addAll(rectangle, labelID);
        setTopAnchor(labelID, 1.0);
        setLeftAnchor(labelID, 1.0);
    }

    public int getID() {
        return ID;
    }

    public int getPathID() {
        return pathID;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
