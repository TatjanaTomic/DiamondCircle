package model.history;

import java.io.Serializable;
import java.util.List;

public class PlayerHistory implements Serializable {
    private static final String PLAYER = "Igrac ";

    private final String playerName;
    private final List<FigureHistory> figuresHistoryList;

    public PlayerHistory(String playerName, List<FigureHistory> figuresHistoryList) {
        this.playerName = playerName;
        this.figuresHistoryList = figuresHistoryList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PLAYER).append(" - ").append(playerName).append('\n');
        for (var figuresHistoryUnit : figuresHistoryList) {
            stringBuilder.append(figuresHistoryUnit.toString());
        }
        stringBuilder.append('\n');

        return stringBuilder.toString();
    }
}
