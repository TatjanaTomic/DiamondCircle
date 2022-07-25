package model.util;

import model.figure.Figure;
import model.game.Game;
import model.game.Simulation;
import model.history.FigureHistory;
import model.history.GameHistory;
import model.history.PlayerHistory;
import model.player.Player;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class HistoryUtil {

    private static final String historyPath = "." + File.separator + "history" + File.separator;
    private static final String GAME = "IGRA_";
    private static final String TXT = ".txt";
    private static final String DATE_FORMAT = "hh-mm-ss";

    public static File[] getHistoryFiles() {
        File historyDirectory = new File(historyPath);

        if(!historyDirectory.exists()) {
            historyDirectory.mkdir();
        }

        return historyDirectory.listFiles();
    }

    public static void serialize(Simulation simulation) {

        GameHistory gameHistory = makeGameHistory(simulation);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String fileName = GAME + simpleDateFormat.format(new Date()) + TXT;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(historyPath + fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gameHistory);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            LoggerUtil.log(HistoryUtil.class, e);
        }
    }

    public static GameHistory deserialize(String fileName) {
        GameHistory gameHistory = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(historyPath + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            gameHistory = (GameHistory) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            LoggerUtil.log(HistoryUtil.class, e);
        }

        return gameHistory;
    }

    private static GameHistory makeGameHistory(Simulation simulation) {
        List<PlayerHistory> playersHistoryList = new LinkedList<>();
        for (Player p : simulation.getPlayersFinished()) {
            List<FigureHistory> figuresHistoryList = new LinkedList<>();
            for (Figure f : p.getFigures()) {
                figuresHistoryList.add(new FigureHistory(f.getID(), f.getClass().getSimpleName(),
                        f.getColor(), f.isReachedToEnd(), f.getCrossedFields()));
            }

            playersHistoryList.add(new PlayerHistory(p.getID(), p.getName(), figuresHistoryList));
        }

        return new GameHistory(Game.timeCounter.getTimeInSeconds(), playersHistoryList);
    }
}
