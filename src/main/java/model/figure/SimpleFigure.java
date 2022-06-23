package model.figure;

import model.exception.IllegalStateOfGameException;
import model.field.Field;
import model.player.Player;

public class SimpleFigure extends Figure {

    public SimpleFigure(FigureColor color, Player player) {
        super(color, player);
    }

    @Override
    public void move(int offset) throws IllegalStateOfGameException {
        if(finishedPlaying)
            throw new IllegalStateOfGameException("Trying to move figure that finished playing!");

        Field nextField = null;
        if(!startedPlaying) {
            nextField = getNextField();
            startedPlaying = true;
        }



    }

    private Field getNextField() {
        //TODO : dovrsi ovo
        return null;
    }
}
