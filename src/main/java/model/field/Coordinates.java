package model.field;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int calculateDistance(Coordinates start, Coordinates end){
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

}
