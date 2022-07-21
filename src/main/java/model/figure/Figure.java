package model.figure;

import controller.MainViewController;
import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.game.DiamondCircleApplication;
import model.game.Game;

import java.util.LinkedList;
import java.util.List;

public abstract class Figure implements IMoveable {

    private final FigureColor color;
    private final String playerName;
    private final String imageName;
    protected GameField currentField;
    protected GameField nextField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;
    protected int collectedDiamonds = 0;
    protected int numberOfFields = 0;

    protected static int _id = 1;
    protected final int ID;

    protected final List<Integer> crossedFields;

    protected boolean reachedToEnd;

    protected static final String OFFSET_ERROR_MESSAGE = "Illegal value of offset!";

    public Figure(FigureColor color, String playerName, String imageName) {
        this.color = color;
        this.playerName = playerName;
        this.imageName = imageName;
        crossedFields = new LinkedList<>();
        currentField = null;
        ID = _id;
        _id++;
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

    public void collectDiamond() {
        collectedDiamonds++;
    }

    public int getID() {
        return ID;
    }

    public void setCurrentField(GameField field) {
        currentField = field;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public GameField getCurrentField() {
        return currentField;
    }

    public GameField getNextField() {
        return nextField;
    }

    public boolean isReachedToEnd() {
        return reachedToEnd;
    }

    public void setReachedToEnd(boolean value) {
        reachedToEnd = value;
    }

    public List<Integer> getCrossedFields() {
        return crossedFields;
    }

    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        System.out.println(getClass().getSimpleName() + " Figure is moving - offset: " + offset + " - diamonds: " + collectedDiamonds);
        numberOfFields = calculateNumberOfFields(offset);
        System.out.println("number of fields (offset + diamonds): " + numberOfFields);
        collectedDiamonds = 0;

        for(int i = 0; i < numberOfFields; i++) {
            int currentPathID;
            if(finishedPlaying) {
                //TODO : Da li treba exception ili da na neki nacin zavrsim pomjeranje ?
                throw new IllegalStateOfGameException("Cannot move figure that finished playing!");
            }

            if(!startedPlaying) {
                startedPlaying = true;
                currentPathID = 0;
            }
            else {
                currentPathID = currentField.getPathID();
            }

            synchronized (MainViewController.map) {
                nextField = calculateNextField(currentPathID);

                if(currentField != null) {
                    if(currentField.isDiamondAdded()) {
                        currentField.setDiamondAdded(false);
                    }
                    currentField.removeAddedFigure();
                }

                DiamondCircleApplication.mainController.setDescription(false);

                currentField = nextField;
                currentField.setAddedFigure(this);
                crossedFields.add(currentField.getID());

                if(currentField.isDiamondAdded()) {
                    currentField.setDiamondAdded(false);
                }
            }

            System.out.println("i: " + i);

            Thread.sleep(1000);

            if(currentField.isEndField()) {
                if(Game.simulation == null)
                    throw new IllegalStateOfGameException();

                // figura je stigla do cilja, igra je za nju uspjesno zavrsena
                Game.simulation.figureFinishedPlaying(this, true);

                synchronized (MainViewController.map) {
                    currentField.removeAddedFigure();
                }

                currentField = null;
                nextField = null;
                finishedPlaying = true;
                return;
            }
        }
    }

    private GameField calculateNextField(int currentFieldID) throws IllegalStateOfGameException {
        int nextFieldID = currentFieldID + 1;
        GameField nextField = MainViewController.getFieldByPathID(nextFieldID);

        if(nextField == null) {
            System.out.println("########## nextField is NULL    ID: " + nextFieldID + "    player: " + getPlayerName()
                    + "    figure: " + getClass().getSimpleName() + " " + getColor());
            throw new IllegalStateOfGameException();
        }
        else {
            if(nextField.isFigureAdded())
                return calculateNextField(nextFieldID);
            else
                return nextField;
        }
    }

    protected abstract int calculateNumberOfFields(int offset);

    @Override
    public String toString() {
        return ID + " - " + getClass().getSimpleName();
    }
}
