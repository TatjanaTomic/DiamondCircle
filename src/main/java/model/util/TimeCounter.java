package model.util;

import model.game.DiamondCircleApplication;

public class TimeCounter extends Thread {

    private int timeInSeconds = 0;

    @Override
    public void run() {

        while(isAlive()) {
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
}
