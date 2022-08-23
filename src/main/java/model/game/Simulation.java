package model.game;

import controller.MainViewController;
import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.GameField;
import model.figure.*;
import model.player.Player;
import model.util.HistoryUtil;
import model.util.LoggerUtil;

import java.util.*;

public class Simulation implements Runnable {
    private static final String SPECIAL_CARD = "SpecialCard";
    private static final String PLAYER = "Player ";
    private static final String NO_FIGURES_ERROR_MESSAGE = " has no figures for playing!";

    private final List<Player> playersInGame;
    private final List<Player> playersFinished;
    private final List<Coordinates> pathForHoles = new ArrayList<>();

    private Player currentPlayer;

    private volatile boolean exit = false;

    public Simulation(List<Player> players) {
        this.playersInGame = players;
        this.playersFinished = new LinkedList<>();

        pathForHoles.addAll(Game.gamePath.subList(1, Game.gamePath.size() - 1));
    }

    public List<Player> getPlayers() {
        return playersInGame;
    }

    public List<Player> getPlayersFinished() {
        return playersFinished;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void run() {

        while(!exit) {

            while(hasPlayersForPlaying()) {

                // sljedeci kod je u sustini jedan potez
                try {
                    // dolazi na red sljedeci igrac
                    currentPlayer = nextPlayer();

                    // izvlaci kartu
                    Card currentCard = Deck.getInstance().takeCard();
                    showCard(currentCard);

                    // dohvati figuru kojom igrac trenutno igra
                    if(!currentPlayer.hasFiguresForPlaying())
                        throw new IllegalStateOfGameException(PLAYER + currentPlayer.getName() + NO_FIGURES_ERROR_MESSAGE);
                    Figure currentFigure = currentPlayer.getCurrentFigure();

                    if(currentCard.getClass().getSimpleName().equals(SPECIAL_CARD)) {
                        DiamondCircleApplication.mainController.setDescription(true);
                        specialCardOnMove();
                    }
                    else {
                        currentFigure.move(((SimpleCard) currentCard).getOffset());
                    }

                } catch (Exception e) {
                    LoggerUtil.logAsync(getClass(), e);
                }

            }

            if(!hasPlayersForPlaying()) {
                HistoryUtil.serialize(this);
                Game.finishGame();
                DiamondCircleApplication.mainController.resetView();
                break;
            }
        }
    }

    private Player nextPlayer() {
        Player player = playersInGame.get(0);
        playersInGame.remove(0);
        playersInGame.add(player);

        return player;
    }

    public boolean hasPlayersForPlaying(){
        return playersInGame.size() > 0;
    }

    private void showCard(Card currentCard) {
        DiamondCircleApplication.mainController.setCard(currentCard.getImageName());
    }

    private void specialCardOnMove() throws InterruptedException, IllegalStateOfGameException {

        final int minimum = 1;
        final int maximum = Game.gamePath.size() / 2;
        int numberOfHoles = (new Random()).nextInt(maximum - minimum + 1) + minimum;

        // set holes
        Collections.shuffle(pathForHoles);
        synchronized (MainViewController.map) {
            for(int i = 0; i < numberOfHoles; i++) {
                Coordinates c = pathForHoles.get(i);
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                gameField.setHoleAdded(true);
            }
        }

        Thread.sleep(1000);

        // unset holes
        synchronized (MainViewController.map) {
            for (Coordinates c : Game.gamePath) {
                GameField gameField = (GameField) MainViewController.map[c.getX()][c.getY()];
                if(gameField.isHoleAdded() && gameField.isFigureAdded() && !(gameField.getAddedFigure() instanceof HoveringFigure)) {
                    figureFinishedPlaying(gameField.getAddedFigure(), false);
                    gameField.removeAddedFigure();
                }
                gameField.setHoleAdded(false);
            }
        }
    }

    public void figureFinishedPlaying(Figure figure, boolean isSuccessful) throws IllegalStateOfGameException {
        Player player = getPlayerByName(figure.getPlayerName());
        figure.finishGameForFigure(isSuccessful);

        assert player != null;
        if(!player.getCurrentFigure().equals(figure)) {
            throw new IllegalStateOfGameException();
        }

        player.changeCurrentFigure();

        if(!player.hasFiguresForPlaying()) {
            playersInGame.remove(player);
            playersFinished.add(player);
        }

    }

    private Player getPlayerByName(String playerName) {
        for(Player player : playersInGame) {
            if(player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public void stop() {
        exit = true;
    }
}