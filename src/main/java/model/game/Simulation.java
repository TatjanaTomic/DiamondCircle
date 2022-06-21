package model.game;

import model.figure.*;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private ArrayList<FigureColor> colors = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    public Simulation() {
        for(FigureColor color : FigureColor.values())
            colors.add(color);

        BuildSimulationParameters();
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void BuildSimulationParameters() {
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
            int typeNumber = random.nextInt(2);

            switch (typeNumber) {
                case 0 :
                    figures.add(new SimpleFigure(color));
                    break;
                case 1 :
                    figures.add(new HoveringFigure(color));
                    break;
                case 2 :
                    figures.add(new SuperFastFigure(color));
                    break;
            }
        }

        return figures;
    }

}
