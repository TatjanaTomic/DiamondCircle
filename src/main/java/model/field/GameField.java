package model.field;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.exception.IllegalStateOfGameException;
import model.figure.Figure;

import java.io.File;

public class GameField extends Field {

    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String DIAMOND_IMAGE = "diamond2.png";

    private final Color initialColor;
    private final Color initialBorderColor = Color.BLACK;
    private final Color backgroundColor;
    private final Color borderColor;

    private ImageView diamondImage;
    private ImageView figureImage;

    private boolean isStart = false;
    private boolean isEnd = false;

    private boolean diamondAdded = false;
    private boolean holeAdded = false;
    private boolean isFigureAdded = false;
    private Figure addedFigure = null;

    private static int _id = 1;

    public GameField(Coordinates coordinates, double width, double height, Color initialColor) {
        super(_id++, coordinates, width, height);

        this.initialColor = initialColor;
        this.backgroundColor = initialColor;
        this.borderColor = initialBorderColor;

        rectangle.setStroke(this.initialBorderColor);
        rectangle.setFill(this.initialColor);

        setInitialContent();
    }

    public GameField(Coordinates coordinates, double width, double height, Color initialColor, boolean isStart, boolean isEnd) {
        super(_id++, coordinates, width, height);

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

        setInitialContent();
    }

    private void setInitialContent() {
        diamondImage = new ImageView();
        diamondImage.setFitHeight(20);
        diamondImage.setFitWidth(20);
        diamondImage.setImage(new Image
                (new File( IMAGES_PATH + DIAMOND_IMAGE).toURI().toString()));
        diamondImage.setVisible(false);

        getChildren().add(diamondImage);
        setRightAnchor(diamondImage, 1.0);
        setTopAnchor(diamondImage, 1.0);

        figureImage = new ImageView();
        figureImage.setFitWidth(40);
        figureImage.setFitHeight(40);
        figureImage.setImage(null);

        getChildren().add(figureImage);
        setBottomAnchor(figureImage, 4.0);
        setLeftAnchor(figureImage, 2.0);
    }

    public boolean isDiamondAdded() {
        return diamondAdded;
    }

    public void setDiamondAdded(boolean value) {
        // Ako se uklanja dijamant sa nekog polja, a na tom polju je bio dijamant i bila figura, figura ga pokupi
        // Ovu funkciju pozivam sa setDiamondAdded(false) u tri slucaja:
        // 1. kad figura dolazi na polje pa ako zatekne dijamant ona ga pokupi
        // 2. kada figura odlazi sa polja, ako se u medjuvremenu postavio dijamant, takodje ga moze pokupiti
        // 3. ako figura stoji duze vrijeme na jednom polju i za vrijeme njenog stajanja se pojavi i sklanja dijamant, takodje ga ona kupi
        if(diamondAdded && !value && isFigureAdded) {
            addedFigure.collectDiamond();
            System.out.println("***Collected one diamond, figure: " + addedFigure.getColor() +
                    addedFigure.getClass().getSimpleName() +
                    ", field " + ID);
        }

        diamondAdded = value;
        diamondImage.setVisible(value);
    }

    public boolean isHoleAdded() {
        return holeAdded;
    }

    public boolean isEndField() {
        return isEnd;
    }

    public void setHoleAdded(boolean value) {
        holeAdded = value;
        if (value) {
            rectangle.setFill(Color.BLACK);
        } else {
            rectangle.setFill(this.initialColor);
        }
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
        showFigure(figure.getImageName());
    }

    private void showFigure(String imageName) {
        Platform.runLater(() -> figureImage.setImage(new Image
                (new File(IMAGES_PATH + imageName).toURI().toString())));
    }

    public void removeAddedFigure() throws IllegalStateOfGameException {
        if(addedFigure == null)
            throw new IllegalStateOfGameException();

        addedFigure = null;
        isFigureAdded = false;
        Platform.runLater(() -> figureImage.setImage(null));
    }

}
