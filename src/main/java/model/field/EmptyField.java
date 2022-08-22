package model.field;

import javafx.scene.paint.Color;

public class EmptyField extends Field {

    private static final Color borderColor = Color.GRAY;
    private static final Color backgroundColor = Color.DARKGRAY;
    private static final int emptyFieldPathID = -1;

    public EmptyField(Coordinates coordinates, double width, double height) {
        super(emptyFieldPathID, coordinates, width, height);
        rectangle.setStroke(borderColor);
        rectangle.setFill(backgroundColor);
    }
}
