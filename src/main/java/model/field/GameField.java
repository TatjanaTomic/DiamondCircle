package model.field;

import javafx.scene.paint.Color;

public class GameField extends Field {

    private boolean isStart = false;
    private boolean isEnd = false;

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color color) {
        super(fieldContent, coordinates, width, height);

        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(color);
    }

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color color, boolean isStart, boolean isEnd) {
        super(fieldContent, coordinates, width, height);

        this.isStart = isStart;
        this.isEnd = isEnd;

        if(isStart)
            rectangle.setStroke(Color.GOLD);
        else if(isEnd)
            rectangle.setStroke(Color.WHITE);
        else
            rectangle.setStroke(Color.BLACK);

        rectangle.setFill(color);
    }
}
