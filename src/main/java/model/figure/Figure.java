package model.figure;

public abstract class Figure implements IRunnable {

    private FigureColor color;

    public Figure(FigureColor color) {
        this.color = color;
    }

    public FigureColor getColor() {
        return color;
    }


    public abstract void run();
}
