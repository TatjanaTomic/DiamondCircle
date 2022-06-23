package model.player;

import model.figure.Figure;
import model.figure.FigureColor;

import java.util.List;

public class Player {
    private final String name;
    private final FigureColor color;
    private final List<Figure> figures;

    private boolean hasFigures;
    private Figure currentFigure;

    public Player(String name, FigureColor color, List<Figure> figures) {
        this.name = name;
        this.color = color;
        this.figures = figures;
        currentFigure = figures.get(0);
    }

    public String getName() {
        return name;
    }

    public FigureColor getColor() { return color; }

    public List<Figure> getFigures() { return figures; }

    public boolean hasFiguresForPlaying() {
        return hasFigures;
    }

    public Figure getCurrentFigure() {
        return currentFigure;
    }

    private void setCurrentFigure(Figure figure) {
        currentFigure = figure;
    }
}
