package model.util;

import controller.MainViewController;
import model.game.DiamondCircleApplication;
import model.game.Game;

public class TimeCounter extends Thread {

    private int timeInSeconds = 0;

    @Override
    public void run() {

        while(!Game.finished) {

            synchronized (MainViewController.map) {

                if(Game.paused) {
                    try {
                        MainViewController.map.wait();
                    } catch (InterruptedException e) {
                        LoggerUtil.log(getClass(), e);
                    }
                }

                DiamondCircleApplication.mainController.updateTime(timeInSeconds);
            }

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
