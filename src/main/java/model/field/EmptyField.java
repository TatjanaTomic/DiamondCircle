package model.field;

import javafx.scene.paint.Color;

public class EmptyField extends Field {

    public EmptyField(String fieldContent, double x, double y, double width, double height) {
        super(fieldContent, x, y, width, height);
        rectangle.setStroke(Color.GRAY);
        rectangle.setFill(Color.SILVER);
    }
}
