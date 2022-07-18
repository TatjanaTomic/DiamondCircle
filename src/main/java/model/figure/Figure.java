package model.figure;

import controller.MainViewController;
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

    public void collectDiamond() {
        collectedDiamonds++;
    }

    public void setCurrentField(GameField field) {
        currentField = field;
    }

    public GameField getCurrentField() {
        return currentField;
    }

    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {
        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        System.out.println(getClass().getSimpleName() + " Figure is moving - offset: " + offset + " - diamonds: " + collectedDiamonds);
        int numberOfFields = calculateNumberOfFields(offset);
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
                GameField nextField = getNextField(currentPathID);

                if(currentField != null) {
                    if(currentField.isDiamondAdded()) {
                        //collectedDiamonds++;
                        //System.out.println(getClass().getSimpleName() + " " + color + " pokupila dijamant, trenutni broj dijamanata: " + collectedDiamonds);
                        currentField.setDiamondAdded(false);
                    }
                    currentField.removeAddedFigure();
                }

                currentField = nextField;
                currentField.setAddedFigure(this);

                if(currentField.isDiamondAdded()) {
                    //collectedDiamonds++;
                    //System.out.println(getClass().getSimpleName() + " " + color + " pokupila dijamant, trenutni broj dijamanata: " + collectedDiamonds);
                    currentField.setDiamondAdded(false);
                }
            }

            System.out.println("i: " + i);

            Thread.sleep(1000);
        }
    }

    private GameField getNextField(int currentFieldID) throws IllegalStateOfGameException {
        int nextFieldID = currentFieldID + 1;
        GameField nextField = MainViewController.getFieldByPathID(nextFieldID);

        if(nextField == null) {
            System.out.println("########## nextField is NULL    ID: " + nextFieldID + "    player: " + getPlayerName()
                    + "    figure: " + getClass().getSimpleName() + " " + getColor());
            throw new IllegalStateOfGameException();
        }
        else {
            if(nextField.isFigureAdded())
                return getNextField(nextFieldID);
            else
                return nextField;
        }
    }

    protected abstract int calculateNumberOfFields(int offset);
}
