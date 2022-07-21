package model.game;

import controller.MainViewController;
import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.exception.IllegalStateOfGameException;
import model.field.Coordinates;
import model.field.GameField;
import model.figure.*;
import model.history.FigureHistory;
import model.history.GameHistory;
import model.history.PlayerHistory;
import model.player.Player;
import model.util.TimeCounter;
import model.util.LoggerUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Simulation implements Runnable {

    public static int move = 0;

    private static final String SPECIAL_CARD = "SpecialCard";

    private final List<Player> players;
    private final List<Player> playersInGame;
    private final List<Player> playersFinished;
    private final int n = Game.n; // number of holes that will be generated
    private final List<Coordinates> pathForHoles = new ArrayList<>();

    private Player currentPlayer;
    private Card currentCard;

    public boolean isStarted = false;
    public boolean isFinished = false;

    private volatile boolean exit = false;

    public Simulation(List<Player> players) {
        this.players = players;
        this.playersInGame = players;
        this.playersFinished = new LinkedList<>();

        pathForHoles.addAll(Game.gamePath.subList(1, Game.gamePath.size() - 1));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    @Override
    public void run() {

        isStarted = true;

        while(!exit) {

            while(hasPlayersForPlaying()) {

                // sljedeci kod je u sustini jedan potez
                try {
                    move++;
                    System.out.println();
                    System.out.print("Potez: " + move);

                    // dolazi na red sljedeci igrac
                    currentPlayer = nextPlayer();
                    System.out.print("    Player: " + currentPlayer.getName());

                    // izvlaci kartu
                    currentCard = Deck.getInstance().takeCard();
                    System.out.print("    Card: " + currentCard.getClass().getSimpleName());
                    showCard(currentCard);

                    // dohvati figuru kojom igrac trenutno igra
                    if(!currentPlayer.hasFiguresForPlaying())
                        throw new IllegalStateOfGameException("Player " + currentPlayer.getID() + " has no figures for playing!");
                    Figure currentFigure = currentPlayer.getCurrentFigure();
                    System.out.println("    Figure: " + currentFigure.getClass().getSimpleName());

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
                Game.numberOfGames++;
                DiamondCircleApplication.mainController.updateNumberOfGames();

                // TODO : Treba zaustaviti i ostale tredove
                // TODO : Serijalizovati istoriju igre

                testSerialization();

                DiamondCircleApplication.mainController.resetView();
                Game.finishGame();
                break;
            }
        }
    }

    private void testSerialization() {
        List<PlayerHistory> playersHistoryList = new LinkedList<>();
        for (Player p : playersFinished) {
            List<FigureHistory> figuresHistoryList = new LinkedList<>();
            for (Figure f : p.getFigures()) {
                figuresHistoryList.add(new FigureHistory(f.getID(), f.getClass().getSimpleName(),
                        f.getColor(), f.isReachedToEnd(), f.getCrossedFields()));
            }

            playersHistoryList.add(new PlayerHistory(p.getID(), p.getName(), figuresHistoryList));
        }
        GameHistory gameHistory = new GameHistory(Game.timeCounter.getTimeInSeconds(), playersHistoryList);

        SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss");
        String fileName = "IGRA_" + sdf.format(new Date()) + ".txt";

        try {
            FileOutputStream pisac = new FileOutputStream("." + File.separator + "history" + File.separator + fileName);
            ObjectOutputStream upisObjekta = new ObjectOutputStream(pisac);
            upisObjekta.writeObject(gameHistory.toString());
            upisObjekta.close();
            pisac.close();
        } catch (Exception e) {
            LoggerUtil.logAsync(getClass(), e);
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

        // set holes
        Collections.shuffle(pathForHoles);
        synchronized (MainViewController.map) {
            for(int i = 0; i < n; i++) {
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

    public void figureFinishedPlaying(Figure figure, boolean isSuccessfull) throws IllegalStateOfGameException {
        Player player = getPlayerByName(figure.getPlayerName());
        figure.setReachedToEnd(isSuccessfull);

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