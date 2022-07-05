package model.history;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameHistory implements Serializable {
    private static String TOTAL_TIME = "Ukupno vrijeme trajanja igre: ";

    private int time;
    private List<PlayerHistory> playersHistoryList;

    public GameHistory() {
        time = 0;
        playersHistoryList = new LinkedList<>();
    }

    public void setTime(int value) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public List<PlayerHistory> getPlayersHistoryList() {
        return playersHistoryList;
    }

    public void addPlayerHistory(PlayerHistory playerHistory) {
        playersHistoryList.add(playerHistory);
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
