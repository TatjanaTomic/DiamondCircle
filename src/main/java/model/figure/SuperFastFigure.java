package model.figure;

import model.exception.IllegalStateOfGameException;
import model.player.Player;

public class SuperFastFigure extends Figure {

    private static final String IMAGE_NAME = "SuperFastFigure.png";

    public SuperFastFigure(FigureColor color, String playerName) {super(color, playerName, color + IMAGE_NAME);}

    @Override
    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        System.out.println("SuperFast figure is moving - offset: " + offset + " - diamonds: " + collectedDiamonds);
        int numberOfFields = (2 * offset) + collectedDiamonds;
        collectedDiamonds = 0;

        for(int i = 0; i < numberOfFields; i++) {
            System.out.println("i: " + i);

            Thread.sleep(1000);
        }
    }
}
