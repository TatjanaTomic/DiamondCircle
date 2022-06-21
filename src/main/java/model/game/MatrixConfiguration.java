package model.game;

import model.field.Coordinates;

public class MatrixConfiguration {

    public static Coordinates[] gamePath;
    public static Coordinates[] emptyPath;

    private static MatrixConfiguration instance = null;

    private MatrixConfiguration() {

    }

    public static MatrixConfiguration getInstance() {
        if (instance == null)
            instance = new MatrixConfiguration();

        return instance;
    }


}