package model.util;

import model.game.DiamondCircleApplication;

public class TimeCounter implements Runnable {

    private int timeInSeconds = 0;
    private volatile boolean exit = false;

    @Override
    public void run() {

        while(!exit) {
            DiamondCircleApplication.mainController.updateTime(timeInSeconds);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LoggerUtil.log(getClass(), e);
            }

            timeInSeconds++;
        }
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public void stop() {
        exit = true;
    }
}
