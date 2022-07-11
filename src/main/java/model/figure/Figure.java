package model.figure;

import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.player.Player;

public abstract class Figure implements IMoveable {

    private final FigureColor color;
    private final Player player;
    protected GameField currentField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;

    public Figure(FigureColor color) {
        this.color = color;
        this.player = null;
        currentField = null;
    }

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

    public abstract void move(int offset) throws IllegalStateOfGameException, InterruptedException;

    public void setCurrentField(GameField field) {
        currentField = field;
    }

    public GameField getCurrentField() {
        return currentField;
    }

}
