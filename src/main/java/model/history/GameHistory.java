package model.history;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameHistory implements Serializable {
    private static final String TOTAL_TIME = "Ukupno vrijeme trajanja igre: ";

    private int time;
    private List<PlayerHistory> playersHistoryList;

    public GameHistory(int time, List<PlayerHistory> playersHistoryList) {
        this.time = time;
        this.playersHistoryList = playersHistoryList;
    }

    public void setTime(int value) {
        this.time = value;
    }

    public int getTime() {
        return time;
    }

    public List<PlayerHistory> getPlayersHistoryList() {
        return playersHistoryList;
    }

    public void setPlayersHistoryList(List<PlayerHistory> playersHistoryList) {
        this.playersHistoryList = playersHistoryList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var playersHistoryUnit : playersHistoryList) {
            stringBuilder.append(playersHistoryUnit.toString());
        }
        stringBuilder.append(TOTAL_TIME).append(time);

        return stringBuilder.toString();
    }

}
