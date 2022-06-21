package model.field;

import javafx.scene.paint.Color;

public class EmptyField extends Field {

    public EmptyField(String fieldContent, Coordinates coordinates, double width, double height) {
        super(fieldContent, coordinates, width, height);
        rectangle.setStroke(Color.GRAY);
        rectangle.setFill(Color.LIGHTGRAY);
    }
}
