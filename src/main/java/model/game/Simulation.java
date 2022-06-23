package model.game;

import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.card.SpecialCard;
import model.exception.IllegalStateOfGameException;
import model.figure.*;
import model.player.Player;
import model.util.Util;

import java.nio.Buffer;
import java.util.*;

public class Simulation extends Thread {

    private final List<Player> players;
    private final GhostFigure ghostFigure;
    private final int n; // number of holes that will be generated

    private boolean isStarted = false;
    private boolean isFinished = false;

    public Simulation(List<Player> players) {
        this.players = players;
        ghostFigure = new GhostFigure();
        n = Game.n;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void run() {
        isStarted = true;

        ghostFigure.start();

        while(isAlive()) {

            while(hasPlayersForPlaying()) {

                // sljedeci kod je u sustini jedan potez
                try {
                    //dolazi na red sljedeci igrac
                    Player currentPlayer = nextPlayer();
                    System.out.println("Current player: " + currentPlayer.getName());

                    //izvlaci kartu
                    Card currentCard = Deck.getInstance().takeCard();
                    System.out.println("  Current card: " + currentCard.getClass().getSimpleName());

                    //dohvati figuru kojom igrac trenutno igra
                    if(!currentPlayer.hasFiguresForPlaying())
                        throw new IllegalStateOfGameException("Player " + currentPlayer.getID() + " has no figures for playing!");
                    Figure currentFigure = currentPlayer.getCurrentFigure();

                    //Ako je karta specijalna, samo se generisu rupe, ne pomjera se trenutna figura
                    if(currentCard.getClass().getSimpleName().equals("SpecialCard")) {
                        //TODO : Dovrsi setHoles() funkciju
                        setHoles();
                    }
                    else {
                        int offset = ((SimpleCard) currentCard).getOffset();
                        currentFigure.move(offset);
                    }

                    //TODO : Obrisi ovaj sleep !
                    sleep(3000);

                } catch (Exception e) {
                    Util.logAsync(getClass(), e);
                }

            }
        }
    }

    private Player nextPlayer() {
        Player player = players.get(0);
        players.remove(0);
        players.add(player);

        return player;
    }

    public boolean hasPlayersForPlaying(){
        return players.size() > 0;
    }

    private void setHoles() {

    };
}