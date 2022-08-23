package model.figure;

import controller.MainViewController;
import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.game.DiamondCircleApplication;
import model.game.Game;
import model.util.TimeCounter;

import java.util.LinkedList;
import java.util.List;

public abstract class Figure implements IMovable {

    protected static final String OFFSET_ERROR_MESSAGE = "Illegal value of offset!";
    protected static final String MOVE_FIGURE_ERROR_MESSAGE = "Cannot move figure that finished playing!";
    protected static int _id = 1;

    protected final int ID;
    protected final List<Integer> crossedFields;

    protected GameField currentField;
    protected GameField nextField;
    protected boolean startedPlaying = false;
    protected boolean finishedPlaying = false;
    protected boolean reachedToEnd;
    protected int collectedDiamonds = 0;
    protected int numberOfFields = 0;
    protected int startTimeInSeconds;
    protected int endTimeInSeconds;

    private final FigureColor color;
    private final String playerName;
    private final String imageName;

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

    public List<Integer> getCrossedFields() {
        return crossedFields;
    }

    protected abstract int calculateNumberOfFields(int offset);

    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        numberOfFields = calculateNumberOfFields(offset);
        collectedDiamonds = 0;

        for(int i = 0; i < numberOfFields; i++) {
            int currentPathID;
            if(finishedPlaying) {
                throw new IllegalStateOfGameException(MOVE_FIGURE_ERROR_MESSAGE);
            }

            if(!startedPlaying) {
                startedPlaying = true;
                currentPathID = 0;
                startTimeInSeconds = Game.timeCounter.getTimeInSeconds();
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

            Thread.sleep(1000);

            if(currentField.isEndField()) {
                if(Game.simulation == null)
                    throw new IllegalStateOfGameException();

                synchronized (MainViewController.map) {
                    currentField.removeAddedFigure();
                }

                // figura je stigla do cilja, igra je za nju uspjesno zavrsena
                Game.simulation.figureFinishedPlaying(this, true);
                return;
            }
        }
    }

    private GameField calculateNextField(int currentFieldID) throws IllegalStateOfGameException {
        int nextFieldID = currentFieldID + 1;
        GameField nextField = MainViewController.getFieldByPathID(nextFieldID);

        if(nextField == null) {
            throw new IllegalStateOfGameException();
        }
        else {
            if(nextField.isFigureAdded())
                return calculateNextField(nextFieldID);
            else
                return nextField;
        }
    }

    public void finishGameForFigure(boolean isSuccessful) {
        currentField = null;
        nextField = null;
        finishedPlaying = true;
        endTimeInSeconds = Game.timeCounter.getTimeInSeconds();
        reachedToEnd = isSuccessful;
    }

    public int getTimeInGame() {
        if (!startedPlaying) {
            return 0;
        }

        if (finishedPlaying) {
            return endTimeInSeconds - startTimeInSeconds;
        }
        else {
            return Game.timeCounter.getTimeInSeconds() - startTimeInSeconds;
        }
    }

    @Override
    public String toString() {
        return ID + " - " + getClass().getSimpleName();
    }
}
