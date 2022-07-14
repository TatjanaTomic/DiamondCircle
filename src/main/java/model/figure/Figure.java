package model.figure;

import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.player.Player;

public abstract class Figure implements IMoveable {

    private final FigureColor color;
    private final String playerName;
    private final String imageName;
    protected GameField currentField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;
    protected int collectedDiamonds = 0;

    protected static final String OFFSET_ERROR_MESSAGE = "Illegal value of offset!";

    public Figure(FigureColor color, String playerName, String imageName) {
        this.color = color;
        this.playerName = playerName;
        this.imageName = imageName;
        currentField = null;
    }

    public FigureColor getColor() {
        return color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getImageName() {
        return imageName;
    }

    public abstract void move(int offset) throws IllegalStateOfGameException, InterruptedException;

    public void setCurrentField(GameField field) {
        currentField = field;
    }

    public GameField getCurrentField() {
        return currentField;
    }

    protected void changeField(GameField nextField) throws IllegalStateOfGameException{
        currentField.removeAddedFigure();
        //TODO : Dovrsi changeField()
    }
}
