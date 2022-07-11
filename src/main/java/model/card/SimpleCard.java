package model.card;

public class SimpleCard extends Card {

    private static final String SIMPLE_CARD = "SimpleCard";
    private static final String JPG = ".jpg";

    private int offset;

    public SimpleCard(int offset) {
        super(SIMPLE_CARD + offset + JPG);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
