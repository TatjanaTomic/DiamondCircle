package model.player;

import model.figure.Figure;
import model.figure.FigureColor;

import java.util.List;

public class Player {
    private String name;
    private FigureColor color;
    private List<Figure> figures;

    public Player(String name, FigureColor color, List<Figure> figures) {
        this.name = name;
        this.color = color;
        this.figures = figures;
    }

    public String getName() {
        return name;
    }

    public FigureColor getColor() { return color; }

    public List<Figure> getFigures() { return figures; }
}
