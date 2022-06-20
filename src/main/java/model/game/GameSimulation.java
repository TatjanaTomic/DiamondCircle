package model.game;

import model.figure.Figure;
import model.figure.FigureColor;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSimulation {

    private ArrayList<FigureColor> colors = new ArrayList<>();

    private List<Player> players;

    public GameSimulation() {
        for(FigureColor color : FigureColor.values())
            colors.add(color);

        BuildSimulationParameters();
    }

    private void BuildSimulationParameters() {
        for (String name : Game.players) {
            FigureColor color = GenerateColor();
            List<Figure> figures = new ArrayList<>();

            //players.add(new Player(name, color, figures));
        }
    }

    private FigureColor GenerateColor() {
        Random random = new Random();
        int test = colors.size();
        int number = random.nextInt(test);

        FigureColor color = colors.get(number);
        colors.remove(number);

        return color;
    }

    private List<Figure> GenerateFigures() {
        return null;
    }

}
