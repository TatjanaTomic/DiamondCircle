package model.field;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class GameField extends Field {

    private boolean isStart = false;
    private boolean isEnd = false;

    private boolean diamondAdded = false;
    private boolean holeAdded = false;

    private final Color initialColor;
    private final Color initialBorderColor = Color.BLACK;
    private Color backgroundColor;
    private Color borderColor;

    private Label contentLabel;
    private Label diamondLabel;
    private ImageView diamondImage;

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color initialColor) {
        super(coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        rectangle.setStroke(this.initialBorderColor);
        rectangle.setFill(this.initialColor);

        setInitialContent(fieldContent);
    }

    public GameField(String fieldContent, Coordinates coordinates, double width, double height, Color initialColor, boolean isStart, boolean isEnd) {
        super(coordinates, width, height);

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

        setInitialContent(fieldContent);
    }

    private void setInitialContent(String fieldContent) {
        contentLabel = new Label(fieldContent);
        diamondImage = new ImageView();
        diamondImage.setFitHeight(15);
        diamondImage.setFitWidth(15);
        content.getChildren().addAll(contentLabel, diamondImage);
    }

    public boolean isDiamondAdded() {
        return diamondAdded;
    }

    public void setDiamondAdded(boolean value) {
        diamondAdded = value;

        if(value)
            diamondImage.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("../../view/images/diamond2.png"))));
        else
            diamondImage = new ImageView();
        //TODO : Provjeri da li je else okej, da li će se poremetiti width i height
    }

    public boolean isHoleAdded() {
        return holeAdded;
    }

    public void setHoleAdded(boolean value) {
        holeAdded = value;
    }

    public void setContentLabel(String value) {
        contentLabel.setText(value);
    }
}
