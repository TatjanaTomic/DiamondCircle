package model.history;

import java.io.Serializable;
import java.util.List;

public class GameHistory implements Serializable {
    private static final String TOTAL_TIME = "Ukupno vrijeme trajanja igre: ";
    private static final String SECONDS = "s";

    private final int time;
    private final List<PlayerHistory> playersHistoryList;

    public GameHistory(int time, List<PlayerHistory> playersHistoryList) {
        this.time = time;
        this.playersHistoryList = playersHistoryList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var playersHistoryUnit : playersHistoryList) {
            stringBuilder.append(playersHistoryUnit.toString());
        }
        stringBuilder.append(TOTAL_TIME).append(time).append(SECONDS);

        return stringBuilder.toString();
    }

}
