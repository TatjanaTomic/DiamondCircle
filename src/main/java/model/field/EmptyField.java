package model.field;

import javafx.scene.paint.Color;

public class EmptyField extends Field {

    private final Color borderColor = Color.GRAY;
    private final Color backgroundColor = Color.DARKGRAY;

    public EmptyField(String fieldContent, Coordinates coordinates, double width, double height) {
        super(fieldContent, coordinates, width, height);
        rectangle.setStroke(borderColor);
        rectangle.setFill(backgroundColor);
    }
}
