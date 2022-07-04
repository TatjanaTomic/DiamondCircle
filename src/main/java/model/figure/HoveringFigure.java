package model.figure;

import model.player.Player;

public class HoveringFigure extends Figure {

    public HoveringFigure(FigureColor color) { super(color);}
    public HoveringFigure(FigureColor color, Player player) {
        super(color, player);
    }

    @Override
    public void move(int offset) {

    }
}
