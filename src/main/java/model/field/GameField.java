package model.field;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.exception.IllegalStateOfGameException;
import model.figure.Figure;

import java.util.Objects;

public class GameField extends Field {

    private final Color initialColor;
    private final Color initialBorderColor = Color.BLACK;
    private Color backgroundColor;
    private Color borderColor;

    private Label contentLabel;
    private ImageView diamondImage;

    private boolean isStart = false;
    private boolean isEnd = false;

    private boolean diamondAdded = false;
    private boolean holeAdded = false;
    private boolean isFigureAdded = false;
    private Figure addedFigure = null;

    public GameField(int pathID, String fieldContent, Coordinates coordinates, double width, double height, Color initialColor) {
        super(pathID, fieldContent, coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        rectangle.setStroke(this.initialBorderColor);
        rectangle.setFill(this.initialColor);

        setInitialContent(pathID + fieldContent);
    }

    public GameField(int pathID, String fieldContent, Coordinates coordinates, double width, double height, Color initialColor, boolean isStart, boolean isEnd) {
        super(pathID, fieldContent, coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        this.isStart = isStart;
        this.isEnd = isEnd;

        if(isStart)
            rectangle.setStroke(Color.GOLD);
        else if(isEnd)
            rectangle.setStroke(Color.WHITE);
        else
            rectangle.setStroke(Color.BLACK);

        rectangle.setFill(this.initialColor);

        setInitialContent(pathID + fieldContent);
    }

    private void setInitialContent(String fieldContent) {
        diamondImage = new ImageView();
        diamondImage.setFitHeight(20);
        diamondImage.setFitWidth(20);
        diamondImage.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("../../view/images/diamond2.png"))));
        diamondImage.setVisible(false);

        getChildren().add(diamondImage);
        setRightAnchor(diamondImage, 1.0);
        setTopAnchor(diamondImage, 1.0);
    }

    public boolean isDiamondAdded() {
        return diamondAdded;
    }

    public void setDiamondAdded(boolean value) {
        diamondAdded = value;
        diamondImage.setVisible(value);
    }

    public boolean isHoleAdded() {
        return holeAdded;
    }

    public void setHoleAdded(boolean value) {
        holeAdded = value;
        if(value) {
            rectangle.setFill(Color.BLACK);
        }
        else {
            rectangle.setFill(this.initialColor);
        }
    }

    public void setContentLabel(String value) {
        contentLabel.setText(value);
    }

    public boolean isFigureAdded() {
        return isFigureAdded;
    }

    public Figure getAddedFigure() {
        return addedFigure;
    }

    public void setAddedFigure(Figure figure) throws IllegalStateOfGameException {
        if(addedFigure != null)
            throw new IllegalStateOfGameException();

        addedFigure = figure;
        isFigureAdded = true;
        getTextProperty().setValue(figure.getClass().getSimpleName());
    }

    public void removeAddedFigure() throws IllegalStateOfGameException {
        if(addedFigure == null)
            throw new IllegalStateOfGameException();

        addedFigure = null;
        isFigureAdded = false;
        contentLabel.setText("");
    }

}
