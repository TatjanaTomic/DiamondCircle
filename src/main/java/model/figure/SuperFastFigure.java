package model.figure;

public class SuperFastFigure extends Figure {

    private static final String IMAGE_NAME = "SuperFastFigure.png";

    public SuperFastFigure(FigureColor color, String playerName) {super(color, playerName, color + IMAGE_NAME);}

    @Override
    protected int calculateNumberOfFields(int offset) {
        return (2 * offset) + collectedDiamonds;
    }

}
