package model.game;

import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.card.SpecialCard;
import model.figure.*;
import model.player.Player;
import model.util.Util;

import java.nio.Buffer;
import java.util.*;

public class Simulation extends Thread {

    private final List<Player> players;
    private final GhostFigure ghostFigure;

    private boolean isStarted = false;
    private boolean isFinished = false;

    public Simulation(List<Player> players) {
        this.players = players;
        ghostFigure = new GhostFigure();
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void run() {
        isStarted = true;

        ghostFigure.start();

        while(isAlive()) {

            try {
                //dolazi na red sljedeci igrac
                Player currentPlayer = nextPlayer();
                System.out.println("Current player: " + currentPlayer.getName());

                //izvlaci kartu
                Card currentCard = Deck.getInstance().takeCard();
                System.out.println("  Current card: " + currentCard.getClass().getSimpleName());


                //TODO : Dohvati figuru kojom igrac trenutno igra
                // Pozovi move figure

                sleep(2000);

            } catch (Exception e) {
                Util.logAsync(getClass(), e);
            }

        }
    }

    private Player nextPlayer() {
        Player player = players.get(0);
        players.remove(0);
        players.add(player);

        return player;
    }

}
