package model.figure;

import controller.MainViewController;
import model.field.Coordinates;
import model.field.Field;
import model.field.GameField;
import model.game.Game;
import model.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GhostFigure extends Thread {

    private final int minimum = 2;
    private final int maximum = Game.dimension;

    // It won't set diamont on end field
    private final List<Coordinates> tmpGamePath = Game.gamePath.subList(0, Game.gamePath.size() - 1);

    @Override
    public void run() {

        while(isAlive()) {
            try {
                setDiamonds();

                sleep(5000);

            } catch (Exception e) {
                Util.logAsync(getClass(), e);
            }
        }
    }

    private void setDiamonds() {
        Random random = new Random();
        int numberOfDiamonds = random.nextInt(maximum - minimum + 1) + minimum;

        Collections.shuffle(tmpGamePath);

        synchronized (MainViewController.map) {
            for (Coordinates c : Game.gamePath) {
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                gameField.setDiamondAdded(false);
            }

            for (int i = 0; i < numberOfDiamonds; i++) {
                Coordinates c = tmpGamePath.get(i);
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                gameField.setDiamondAdded(true);
            }
        }
    }
}
