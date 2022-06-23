package model.history;

import java.io.Serializable;
import java.util.LinkedList;

public class MovementHistory implements Serializable {
    private LinkedList<MovementHistoryUnit> movementHistoryList;
    private final String gameDescription;

    public MovementHistory(LinkedList<MovementHistoryUnit> movementHistoryList, String gameDescription) {
        this.movementHistoryList = movementHistoryList;
        this.gameDescription = gameDescription;
    }

    @Override
    public String toString() {
        String output = gameDescription + "\n";
        for(MovementHistoryUnit unit : movementHistoryList) {
            output += unit;
        }

        return output;
    }
}
