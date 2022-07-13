package model.figure;

import controller.MainViewController;
import javafx.application.Platform;
import model.exception.IllegalStateOfGameException;
import model.field.GameField;
import model.game.Game;
import model.player.Player;

public class HoveringFigure extends Figure {

    private static final String IMAGE_NAME = "HoveringFigure.png";
    private static final String OFFSET_ERROR_MESSAGE = "Illegal value of offset!";

    public HoveringFigure(FigureColor color, String playerName) { super(color, playerName, color + IMAGE_NAME);}

    @Override
    public void move(int offset) throws IllegalStateOfGameException, InterruptedException {

        if(offset < 1 || offset > 4) {
            throw new IllegalStateOfGameException(OFFSET_ERROR_MESSAGE);
        }

        System.out.println("Hovering figure is moving - offset: " + offset + " - diamonds: " + collectedDiamonds);
        int numberOfFields = offset + collectedDiamonds;
        collectedDiamonds = 0;

        for(int i = 0; i < numberOfFields; i++) {

            int currentPathID;
            if(!startedPlaying) {
                startedPlaying = true;
                currentPathID = 0;
            }
            else {
                currentPathID = currentField.getPathID();
            }

            GameField nextField = getNextField(currentPathID);
            currentField = nextField;

            Platform.runLater(() -> nextField.getContentLabel().setText("HF"));
            System.out.println("i: " + i);

            Thread.sleep(1000);

        }

    }

    private GameField getNextField(int currentPathID) throws IllegalStateOfGameException {
        int nextPathID = currentPathID + 1;
        GameField nextField = MainViewController.getFieldByPathID(nextPathID);

        assert nextField != null;
        if(!nextField.isFigureAdded()) {
            return nextField;
        }
        else {
            return getNextField(nextPathID);
        }
    }
}
