package model.figure;

import model.player.Player;

public class SuperFastFigure extends Figure {

    private static final String IMAGE_NAME = "SuperFastFigure.png";

    public SuperFastFigure(FigureColor color, String playerName) {super(color, playerName, color + IMAGE_NAME);}

    @Override
    public void move(int offset) {

    }
}
