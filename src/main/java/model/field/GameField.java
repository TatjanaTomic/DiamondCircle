package model.field;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.exception.IllegalStateOfGameException;
import model.figure.Figure;
import model.game.Game;

import java.io.File;

public class GameField extends Field {

    private static final String IMAGES_PATH = "src/main/resources/view/images/";
    private static final String DIAMOND_IMAGE = "diamond2.png";

    private static int redComponent = 215;
    private static int greenComponent = 195;
    private static int blueComponent = 215;

    private final Color backgroundColor;
    private static final Color PURPLE_COLOR = Color.rgb(100,15,115);

    private ImageView diamondImage;
    private ImageView figureImage;

    private boolean isEnd = false;

    private boolean diamondAdded = false;
    private boolean holeAdded = false;
    private boolean isFigureAdded = false;
    private Figure addedFigure = null;

    private static int _id = 1;

    public GameField(Coordinates coordinates, double width, double height) {
        super(_id++, coordinates, width, height);

        backgroundColor = Color.rgb(redComponent, greenComponent, blueComponent);
        rectangle.setFill(backgroundColor);
        rectangle.setStroke(Color.BLACK);

        setInitialContent();
        changeColor();
    }

    public GameField(Coordinates coordinates, double width, double height, boolean isStart, boolean isEnd) {
        super(_id++, coordinates, width, height);

        this.isEnd = isEnd;

        if(isStart) {
            rectangle.setStroke(PURPLE_COLOR);
            backgroundColor = Color.WHITESMOKE;
        }
        else if(isEnd) {
            rectangle.setStroke(Color.GRAY);
            backgroundColor = PURPLE_COLOR;
        }
        else {
            rectangle.setStroke(Color.BLACK);
            backgroundColor = Color.rgb(redComponent, greenComponent, blueComponent);
            changeColor();
        }
        rectangle.setFill(backgroundColor);

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
        setRightAnchor(diamondImage, 2.0);
        setTopAnchor(diamondImage, 2.0);

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

    public boolean isHoleAdded() {
        return holeAdded;
    }

    public boolean isEndField() {
        return isEnd;
    }

    public boolean isFigureAdded() {
        return isFigureAdded;
    }

    public Figure getAddedFigure() {
        return addedFigure;
    }

    public void setHoleAdded(boolean value) {
        holeAdded = value;
        if (value) {
            Platform.runLater(() -> rectangle.setFill(Color.BLACK));
        } else {
            Platform.runLater(() -> rectangle.setFill(this.backgroundColor));
        }
    }

    // Ovu funkciju pozivam sa setDiamondAdded(false) u tri slucaja:
    // 1. kad figura dolazi na polje pa ako zatekne dijamant ona ga pokupi
    // 2. kada figura odlazi sa polja, ako se u medjuvremenu postavio dijamant, ona ga pokupi
    // 3. ako figura stoji duze vrijeme na jednom polju i za vrijeme njenog stajanja se pojavi i sklanja dijamant, takodje ga ona kupi
    public void setDiamondAdded(boolean value) {

        if(diamondAdded && !value && isFigureAdded) {
            addedFigure.collectDiamond();
        }

        diamondAdded = value;
        Platform.runLater(() -> diamondImage.setVisible(value));
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

    private void changeColor() {
        if(Game.dimension == 7) {
            redComponent -= 3;
            greenComponent -= 5;
            blueComponent -= 3;
        }
        else if(Game.dimension == 8 || Game.dimension == 9) {
            redComponent -= 2;
            greenComponent -= 4;
            blueComponent -= 2;
        }
        else if(Game.dimension == 10) {
            redComponent -= 1;
            greenComponent -= 3;
            blueComponent -= 1;
        }
    }

}
