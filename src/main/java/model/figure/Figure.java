package model.figure;

import model.exception.IllegalStateOfGameException;
import model.field.Field;
import model.player.Player;

public abstract class Figure implements IMoveable {

    private final FigureColor color;
    private final Player player;
    protected Field currentField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;

    public Figure(FigureColor color, Player player) {
        this.color = color;
        this.player = player;
        currentField = null;
    }

    public FigureColor getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void move(int offset) throws IllegalStateOfGameException;

    public void setCurrentField(Field field) {
        currentField = field;
    }

    public Field getCurrentField() {
        return currentField;
    }
}
