package model.game;

import model.card.Deck;
import model.exception.ErrorStartingGameException;
import model.figure.*;
import model.player.Player;

import java.util.*;

public abstract class SimulationBuilder {

    private static final String TYPE_ERROR_MESSAGE = "Invalid type of figure.";

    private static final ArrayList<FigureColor> colors = new ArrayList<>();
    private static final List<Player> players = new ArrayList<>();

    public static Simulation build() throws ErrorStartingGameException {

        try {

            colors.addAll(Arrays.asList(FigureColor.values()));

            int i = 1;
            // Dodaju se igraci i njihove figure
            for (String name : Game.playersNames) {
                FigureColor color = GenerateColor();
                //TODO : vrati poziv ove funkcije !!!
                List<Figure> figures = GenerateFigures(color, name);
//                List<Figure> figures = new ArrayList<>();
//                figures.add(new HoveringFigure(color, name));
//                figures.add(new HoveringFigure(color, name));
//                figures.add(new HoveringFigure(color, name));
//                figures.add(new HoveringFigure(color, name));

                players.add(new Player(i++, name, color, figures));
            }

            // Redoslijed igraca se odredjuje na slucajan nacin
            Collections.shuffle(players);

            // Izmijesaju se karte
            Deck.getInstance().shuffleCards();

            return new Simulation(players);
        }
        catch (Exception e) {
            throw new ErrorStartingGameException(e.getMessage());
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
    private static List<Figure> GenerateFigures(FigureColor color, String playerName) {

        List<Figure> figures = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            Random random = new Random();
            int typeNumber = random.nextInt(3);

            switch (typeNumber) {
                case 0 -> figures.add(new SimpleFigure(color, playerName));
                case 1 -> figures.add(new HoveringFigure(color, playerName));
                case 2 -> figures.add(new SuperFastFigure(color, playerName));
                default -> throw new IllegalStateException(TYPE_ERROR_MESSAGE);
            }
        }

        return figures;
    }
}
