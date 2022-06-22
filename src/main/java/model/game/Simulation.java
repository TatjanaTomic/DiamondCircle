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

    private ArrayList<FigureColor> colors = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    private List<Card> cards;

    public Simulation() {
        for(FigureColor color : FigureColor.values())
            colors.add(color);

        BuildSimulationParameters();

        cards = Deck.getInstance().getCards();
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void BuildSimulationParameters() {
        // Add players and their figures
        for (String name : Game.players) {
            FigureColor color = GenerateColor();
            List<Figure> figures = GenerateFigures(color);

            players.add(new Player(name, color, figures));
        }

    }

    // Za svakog igraca odreduje se boja na slucajan nacin
    // Svaki igrac ima razlicitu boju
    private FigureColor GenerateColor() {
        Random random = new Random();
        int number = random.nextInt(colors.size());

        FigureColor color = colors.get(number);
        colors.remove(number);

        return color;
    }

    // Svakom igracu dodjeljuju se 4 figure iste boje
    // Tip figure se odredjuje na slucajan nacin
    // 0 - SimpleFigure
    // 1 - HoveringFigure
    // 2 - SuperFastFigure
    private List<Figure> GenerateFigures(FigureColor color) {

        List<Figure> figures = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            Random random = new Random();
            int typeNumber = random.nextInt(3);

            switch (typeNumber) {
                case 0 -> figures.add(new SimpleFigure(color));
                case 1 -> figures.add(new HoveringFigure(color));
                case 2 -> figures.add(new SuperFastFigure(color));
                default -> throw new IllegalStateException("Invalid type of figure.");
            }
        }

        return figures;
    }

}
