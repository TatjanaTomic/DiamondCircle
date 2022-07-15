package model.figure;

public class SimpleFigure extends Figure {

    private static final String IMAGE_NAME = "SimpleFigure.png";

    public SimpleFigure(FigureColor color, String playerName) { super(color, playerName, color + IMAGE_NAME); }

    @Override
    protected int calculateNumberOfFields(int offset) {
        return offset + collectedDiamonds;
    }

}
