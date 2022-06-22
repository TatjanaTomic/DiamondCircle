package model.field;

import javafx.scene.paint.Color;

public class GameField extends Field {

    private boolean isStart = false;
    private boolean isEnd = false;

    private boolean diamondAdded = false;
    private boolean holeAdded = false;

    private final Color initialColor;
    private final Color initialBorderColor = Color.BLACK;
    private Color backgroundColor;
    private Color borderColor;


    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color initialColor) {
        super(fieldContent, coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        rectangle.setStroke(this.initialBorderColor);
        rectangle.setFill(this.initialColor);
    }

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color initialColor, boolean isStart, boolean isEnd) {
        super(fieldContent, coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        this.isStart = isStart;
        this.isEnd = isEnd;

        if(isStart)
            rectangle.setStroke(Color.GOLD);
        else if(isEnd)
            rectangle.setStroke(Color.WHITE);
        else
            rectangle.setStroke(Color.BLACK);

        rectangle.setFill(this.initialColor);
    }

    public boolean isDiamondAdded() {
        return diamondAdded;
    }

    public void SetDiamondAdded(boolean value) {
        diamondAdded = value;
    }

    public boolean isHoleAdded() {
        return holeAdded;
    }

    public void SetHoleAdded(boolean value) {
        holeAdded = value;
    }
}
