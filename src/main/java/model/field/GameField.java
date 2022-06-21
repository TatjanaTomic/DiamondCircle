package model.field;

import javafx.scene.paint.Color;

public class GameField extends Field {

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color color) {
        super(fieldContent, coordinates, width, height);

        rectangle.setStroke(Color.PURPLE);
        rectangle.setFill(color);
    }
}
