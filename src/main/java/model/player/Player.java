package model.player;

import model.figure.Figure;

import java.util.List;

public class Player {
    private String name;
    private String color;
    private List<Figure> figures;

    public Player(String name, String color, List<Figure> figures) {
        this.name = name;
        this.color = color;
        this.figures = figures;
    }

    public String getName() {
        return name;
    }

    public String getColor() { return color; }

    public List<Figure> getFigures() { return figures; }
}
