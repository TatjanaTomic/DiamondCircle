package model.game;

import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.exception.IllegalStateOfGameException;
import model.figure.*;
import model.history.GameHistory;
import model.history.PlayerHistory;
import model.player.Player;
import model.util.Util;

import java.util.*;

public class Simulation extends Thread {

    public static int move = 0;

    private static final String SPECIAL_CARD = "SpecialCard";
    private static final String SIMPLE_CARD = "SimpleCard";
    private static final String JPG = ".jpg";

    private final GameHistory gameHistory = new GameHistory();
    private final List<Player> players;
    private final GhostFigure ghostFigure;
    private final int n; // number of holes that will be generated

    private boolean isStarted = false;
    private boolean isFinished = false;

    public Simulation(List<Player> players) {
        this.players = players;
        n = Game.n;
        ghostFigure = new GhostFigure();
        addGameHistory();
    }

    private void addGameHistory() {
        for (Player player : players) {
            gameHistory.addPlayerHistory(new PlayerHistory(player.getID(), player.getName()));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void run() {

        ghostFigure.start();

        //TODO : Treba voditi racuna o vremenu igre
        isStarted = true;
        System.out.println("Simulacija pokrenuta u tredu: " + Thread.currentThread().getName());

        while(isAlive()) {

            while(hasPlayersForPlaying()) {

                // sljedeci kod je u sustini jedan potez
                try {
                    move++;
                    System.out.print("Potez: " + move);

                    //dolazi na red sljedeci igrac
                    Player currentPlayer = nextPlayer();
                    System.out.print("    Player: " + currentPlayer.getName());

                    //izvlaci kartu
                    Card currentCard = Deck.getInstance().takeCard();
                    System.out.print("    Card: " + currentCard.getClass().getSimpleName());
                    showCard(currentCard);

                    //dohvati figuru kojom igrac trenutno igra
                    if(!currentPlayer.hasFiguresForPlaying())
                        throw new IllegalStateOfGameException("Player " + currentPlayer.getID() + " has no figures for playing!");
                    Figure currentFigure = currentPlayer.getCurrentFigure();
                    System.out.println("    Figure: " + currentFigure.getClass().getSimpleName());

                    //Ako je karta specijalna, samo se generisu rupe, ne pomjera se trenutna figura
                    if(currentCard.getClass().getSimpleName().equals(SPECIAL_CARD)) {
                        setHoles();
                        Thread.sleep(1000);
                    }
                    else {
                        int offset = ((SimpleCard) currentCard).getOffset();
                        currentFigure.move(offset);
                    }

                    Thread.sleep(3000);

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
        //TODO : Dovrsi setHoles funkciju
    };

    private void showCard(Card currentCard) {
        DiamondCircleApplication.mainController.setCard(currentCard.getImageName());
    }
}