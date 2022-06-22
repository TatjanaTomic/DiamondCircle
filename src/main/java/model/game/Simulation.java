package model.game;

import model.card.Card;
import model.card.Deck;
import model.card.SimpleCard;
import model.card.SpecialCard;
import model.figure.*;
import model.player.Player;

import java.nio.Buffer;
import java.util.*;

public class Simulation {

    private final List<Player> players;
    private final GhostFigure ghostFigure;

    public Simulation(List<Player> players) {
        this.players = players;
        ghostFigure = new GhostFigure();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void startSimulation() {
        ghostFigure.start();
    }
}
