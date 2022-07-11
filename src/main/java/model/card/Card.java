package model.card;

public abstract class Card {
    protected String imageName;

    Card(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
