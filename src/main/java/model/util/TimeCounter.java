package model.util;

import model.game.DiamondCircleApplication;

public class TimeCounter extends Thread {

    private int timeInSeconds = 0;

    @Override
    public void run() {
        while(isAlive()) {
            DiamondCircleApplication.mainController.setTime(timeInSeconds++);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LoggerUtil.logAsync(getClass(), e);
            }
        }
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }
}
