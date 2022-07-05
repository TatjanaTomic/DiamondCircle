package model.history;

import java.util.LinkedList;
import java.util.List;

public class PlayerHistory {
    private static final String PLAYER = "Igrač ";

    private final int playerID;
    private final String playerName;
    private List<FigureHistory> figuresHistoryList;

    public PlayerHistory(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        figuresHistoryList = new LinkedList<>();
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PLAYER).append(playerID).append(" - ").append(playerName);
        for (var figuresHistoryUnit : figuresHistoryList) {
            stringBuilder.append(figuresHistoryUnit.toString());
        }
        stringBuilder.append('\n');

        return stringBuilder.toString();
    }
}
