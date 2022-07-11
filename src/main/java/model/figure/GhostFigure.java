package model.figure;

import controller.MainViewController;
import model.field.Coordinates;
import model.field.GameField;
import model.game.Game;
import model.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GhostFigure extends Thread {

    private final int minimum = 2;
    private final int maximum = Game.dimension;
    private final List<Coordinates> tmpGamePath = new ArrayList<>();

    public GhostFigure() {
        // It won't set diamond on end field
        tmpGamePath.addAll(Game.gamePath.subList(0, Game.gamePath.size() - 1));
    }

    @Override
    public void run() {

        while(isAlive()) {
            try {

                Random random = new Random();
                int numberOfDiamonds = random.nextInt(maximum - minimum + 1) + minimum;

                System.out.println("Pozdrav iz run() metode Ghost figure: " + Thread.currentThread().getName());
                System.out.println("Broj dijamanata: " + numberOfDiamonds);

                Collections.shuffle(tmpGamePath);

                synchronized (MainViewController.map) {

                    System.out.println("POSTAVLJANJE DIJAMANATA " + LocalDateTime.now());

                    for (Coordinates c : Game.gamePath) {
                        GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                        gameField.setDiamondAdded(false);

                        //System.out.println("[" + c.getX() + "][" + c.getY() + "] false");
                    }

                    for (int i = 0; i < numberOfDiamonds; i++) {
                        Coordinates c = tmpGamePath.get(i);
                        GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                        gameField.setDiamondAdded(true);

                        //System.out.println("[" + c.getX() + "][" + c.getY() + "] true");
                    }

                }

                Thread.sleep(5000);

            } catch (Exception e) {
                Util.logAsync(getClass(), e);
            }
        }
    }

    private void setDiamonds() {


    }
}
