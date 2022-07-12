package model.game;

import controller.MainViewController;
import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.GameField;
import model.figure.*;
import model.history.GameHistory;
import model.history.PlayerHistory;
import model.player.Player;
import model.util.TimeCounter;
import model.util.LoggerUtil;

import java.util.*;

public class Simulation extends Thread {

    public static int move = 0;

    private static final String SPECIAL_CARD = "SpecialCard";

    private final GameHistory gameHistory = new GameHistory();
    private final List<Player> players;
    private final GhostFigure ghostFigure;
    private final TimeCounter timeCounter;
    private final int n = Game.n; // number of holes that will be generated
    private final List<Coordinates> pathForHoles = new ArrayList<>();

    private boolean isStarted = false;
    private boolean isFinished = false;

    public Simulation(List<Player> players) {
        this.players = players;
        ghostFigure = new GhostFigure();
        timeCounter = new TimeCounter();
        addGameHistory();
        pathForHoles.addAll(Game.gamePath.subList(1, Game.gamePath.size() - 1));
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

        isStarted = true;

        timeCounter.start();
        ghostFigure.start();

        while(isAlive()) {

            while(hasPlayersForPlaying()) {

                // sljedeci kod je u sustini jedan potez
                try {
                    move++;
                    System.out.print("Potez: " + move);

                    // dolazi na red sljedeci igrac
                    Player currentPlayer = nextPlayer();
                    System.out.print("    Player: " + currentPlayer.getName());

                    // izvlaci kartu
                    Card currentCard = Deck.getInstance().takeCard();
                    System.out.print("    Card: " + currentCard.getClass().getSimpleName());
                    showCard(currentCard);

                    // dohvati figuru kojom igrac trenutno igra
                    if(!currentPlayer.hasFiguresForPlaying())
                        throw new IllegalStateOfGameException("Player " + currentPlayer.getID() + " has no figures for playing!");
                    Figure currentFigure = currentPlayer.getCurrentFigure();
                    System.out.println("    Figure: " + currentFigure.getClass().getSimpleName());

                    // Ako je karta specijalna, samo se generisu rupe, ne pomjera se trenutna figura
                    if(currentCard.getClass().getSimpleName().equals(SPECIAL_CARD)) {
                        setHoles();
                        Thread.sleep(1000);
                        unsetHoles();
                    }
                    else {
                        int offset = ((SimpleCard) currentCard).getOffset();
                        currentFigure.move(offset);
                    }

                } catch (Exception e) {
                    LoggerUtil.logAsync(getClass(), e);
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

    private void showCard(Card currentCard) {
        DiamondCircleApplication.mainController.setCard(currentCard.getImageName());
    }

    private void setHoles() {
        //TODO : Dovrsi setHoles() funkciju - treba implementirati "propadanje" figura

        Collections.shuffle(pathForHoles);

        synchronized (MainViewController.map) {
            for(int i = 0; i < n; i++) {
                Coordinates c = pathForHoles.get(i);
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                gameField.setHoleAdded(true);
            }
        }
    };

    private void unsetHoles() {
        synchronized (MainViewController.map) {
            for (Coordinates c : Game.gamePath) {
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                gameField.setHoleAdded(false);
            }
        }
    }

}