package model.figure;

import model.player.Player;

public class SuperFastFigure extends Figure {

    private static final String IMAGE_NAME = "SuperFastFigure.png";

    public SuperFastFigure(FigureColor color) {super(color, color + IMAGE_NAME);}
    public SuperFastFigure(FigureColor color, Player player) {
        super(color, player, color + IMAGE_NAME);
    }

    @Override
    public void move(int offset) {

    }
}
