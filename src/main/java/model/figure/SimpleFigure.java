package model.figure;

import controller.MainViewController;
import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.player.Player;

public class SimpleFigure extends Figure {

    private static final String IMAGE_NAME = "SimpleFigure.png";

    public SimpleFigure(FigureColor color, String playerName) { super(color, playerName, color + IMAGE_NAME); }

    @Override
    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        System.out.println("Simple figure is moving - offset: " + offset + " - diamonds: " + collectedDiamonds);
        int numberOfFields = offset + collectedDiamonds;
        collectedDiamonds = 0;

        for(int i = 0; i < numberOfFields; i++) {
            System.out.println("i: " + i);

            Thread.sleep(1000);
        }
    }

    private GameField getNextField(int currentFieldID) throws IllegalStateOfGameException {
        GameField nextField = (GameField) MainViewController.getFieldByPathID(currentFieldID + 1);

        //TODO : Vidi sta ces s ovim
        assert nextField != null;
        if(nextField.isFigureAdded())
            return getNextField(currentFieldID + 1);
        else
            return nextField;
    }
}
