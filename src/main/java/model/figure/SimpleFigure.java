package model.figure;

import controller.MainViewController;
import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.player.Player;

public class SimpleFigure extends Figure {

    public SimpleFigure(FigureColor color) { super(color); }

    public SimpleFigure(FigureColor color, Player player) {
        super(color, player);
    }

    @Override
    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
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
                if(currentField != null)
                    currentField.removeAddedFigure();
                GameField nextField = getNextField(currentFieldPathID);
                nextField.setAddedFigure(this);
            }

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
