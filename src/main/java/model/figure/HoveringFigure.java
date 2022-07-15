package model.figure;

public class HoveringFigure extends Figure {

    private static final String IMAGE_NAME = "HoveringFigure.png";

    public HoveringFigure(FigureColor color, String playerName) { super(color, playerName, color + IMAGE_NAME);}

    @Override
    protected int calculateNumberOfFields(int offset) {
        return offset + collectedDiamonds;
    }

}
