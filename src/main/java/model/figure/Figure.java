package model.figure;

import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.player.Player;

public abstract class Figure implements IMoveable {

    private final FigureColor color;
    private final Player player;
    private final String imageName;
    protected GameField currentField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;
    protected int collectedDiamonds = 0;

    public Figure(FigureColor color, String imageName) {
        this.color = color;
        this.player = null;
        this.imageName = imageName;
        currentField = null;

        System.out.println(imageName);
    }

    public Figure(FigureColor color, Player player, String imageName) {
        this.color = color;
        this.player = player;
        this.imageName = imageName;
        currentField = null;

        System.out.println(imageName);
    }

    public FigureColor getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
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

    }
}
