package model.game;

import model.card.Card;
import model.card.Deck;
import model.exception.ErrorStartingGameException;
import model.figure.*;
import model.player.Player;
import model.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class SimulationBuilder {

    private static ArrayList<FigureColor> colors = new ArrayList<>();

    private static List<Player> players = new ArrayList<>();

    public static Simulation build() throws ErrorStartingGameException {

        try {

            for (FigureColor color : FigureColor.values())
                colors.add(color);

            int i = 1;
            // Add players and their figures
            for (String name : Game.players) {
                FigureColor color = GenerateColor();
                List<Figure> figures = GenerateFigures(color);

                players.add(new Player(i++, name, color, figures));
            }

            //Randomize players' order
            Collections.shuffle(players);

            // Shuffle cards
            Deck.getInstance().shuffleCards();

            return new Simulation(players);
        }
        catch (Exception e) {
            throw new ErrorStartingGameException();
        }
    }

    // Za svakog igraca odreduje se boja na slucajan nacin
    // Svaki igrac ima razlicitu boju
    private static FigureColor GenerateColor() {
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
    private static List<Figure> GenerateFigures(FigureColor color) {

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
