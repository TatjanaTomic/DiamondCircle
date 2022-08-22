package model.field;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import model.game.Game;

public abstract class Field extends AnchorPane {

    protected Rectangle rectangle;
    protected final int ID; //ID at whole map
    protected final int pathID; //ID at path for playing

    protected Field(int pathID, Coordinates coordinates, double width, double height) {
        ID = coordinates.getX() * Game.dimension + coordinates.getY() + 1;
        this.pathID = pathID;

        rectangle = new Rectangle(width, height);
        Label labelID = new Label(Integer.toString(ID));

        getChildren().addAll(rectangle, labelID);
        setTopAnchor(labelID, 1.0);
        setLeftAnchor(labelID, 1.0);

        setCache(false);
    }

    public int getID() {
        return ID;
    }

    public int getPathID() {
        return pathID;
    }

    @Override
    public String toString() {
        return Integer.toString(ID);
    }

}
