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

public class GhostFigure extends Thread {

    private static final int minimum = 2;
    private static final int maximum = Game.dimension;

    private final List<Coordinates> pathForDiamonds = new ArrayList<>();

    public GhostFigure() {
        // It won't set diamond on end field
        pathForDiamonds.addAll(Game.gamePath.subList(0, Game.gamePath.size() - 1));
    }

    @Override
    public void run() {

        while(isAlive()) {
            try {
                synchronized (MainViewController.map) {

                    int numberOfDiamonds = new Random().nextInt(maximum - minimum + 1) + minimum;

                    Collections.shuffle(pathForDiamonds);

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
}
