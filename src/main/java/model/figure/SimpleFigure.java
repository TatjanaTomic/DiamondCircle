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

        System.out.println("Figure moved");
        Thread.sleep(1000);

        /*
        if(finishedPlaying)
            throw new IllegalStateOfGameException("Trying to move figure that finished playing!");

        int currentFieldPathID;
        if(!startedPlaying) {
            currentFieldPathID = 0;
            startedPlaying = true;
        }
        else
            currentFieldPathID = currentField.getPathID();

        if(offset < 1 || offset > 4)
            throw new IllegalStateOfGameException("Illegal value for offset!");

        for(int i = 0; i < offset; i++) {
            synchronized (MainViewController.map) {
                //if(currentField != null)
                //    currentField.removeAddedFigure();
                GameField nextField = getNextField(currentFieldPathID);
                //nextField.setAddedFigure(this);
                //TODO : Odkomentariši ovo
            }

            Thread.sleep(1000);
        }
        */

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
