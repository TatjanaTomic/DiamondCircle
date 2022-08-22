package model.figure;

import controller.MainViewController;
import model.field.Coordinates;
import model.field.GameField;
import model.game.Game;
import model.util.LoggerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GhostFigure implements Runnable {

    private static final int minimum = 2;
    private static final int maximum = Game.dimension;

    private final List<Coordinates> pathForDiamonds = new ArrayList<>();

    private volatile boolean exit = false;

    public GhostFigure() {
        // It won't set diamond on end field
        pathForDiamonds.addAll(Game.gamePath.subList(0, Game.gamePath.size() - 1));
    }

    @Override
    public void run() {

        while(!exit) {
            try {
                Random random = new Random();
                int numberOfDiamonds = random.nextInt(maximum - minimum + 1) + minimum;

                Collections.shuffle(pathForDiamonds);

                synchronized (MainViewController.map) {

                    for (Coordinates c : Game.gamePath) {
                        GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                        gameField.setDiamondAdded(false);
                    }

                    for (int i = 0; i < numberOfDiamonds; i++) {
                        Coordinates c = pathForDiamonds.get(i);
                        GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                        gameField.setDiamondAdded(true);
                    }

                }

                Thread.sleep(5000);

            } catch (Exception e) {
                LoggerUtil.log(getClass(), e);
            }
        }
    }

    public void stop() {
        exit = true;
    }
}
