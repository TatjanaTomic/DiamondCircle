package model.card;

public class SimpleCard extends Card {
    private int offset;

    public SimpleCard(int offset) {
        super();
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
