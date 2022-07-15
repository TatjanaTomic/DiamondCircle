package model.card;

public abstract class Card {
    protected final String imageName;

    Card(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
