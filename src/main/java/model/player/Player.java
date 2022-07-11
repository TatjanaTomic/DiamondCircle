package model.player;

import model.exception.IllegalStateOfGameException;
import model.figure.Figure;
import model.figure.FigureColor;

import java.util.List;

public class Player {
    private final String name;
    private final FigureColor color;
    private final List<Figure> figures;
    private final int ID;

    private boolean hasFigures = true;
    private int currentFigureID;
    private Figure currentFigure;

    public Player(int ID, String name, FigureColor color, List<Figure> figures) {
        this.ID = ID;
        this.name = name;
        this.color = color;
        this.figures = figures;
        currentFigure = figures.get(0);
        currentFigureID = 0;
    }

    public int getID() {
        return ID;
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

    public void changeCurrentFigure() throws IllegalStateOfGameException {
        if(!hasFigures) {
            throw new IllegalStateOfGameException();
        }

        if(currentFigureID < 0 || currentFigureID > 3) {
            throw new IllegalStateOfGameException();
        }

        if(currentFigureID == 3) {
            hasFigures = false;
            return;
        }

        currentFigureID += 1;
        currentFigure = figures.get(currentFigureID);
    }


}
