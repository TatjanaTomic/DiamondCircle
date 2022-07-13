package model.game;

import controller.FxmlController;
import controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DiamondCircleApplication extends Application {

    private static final String MAIN_VIEW_FXML = "../../view/MainView.fxml";
    private static final String TITLE = "DiamondCircleGame";

    public static MainViewController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //FxmlController.load(getClass(), MAIN_VIEW_FXML);

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(Objects.requireNonNull(getClass().getResource(MAIN_VIEW_FXML)).openStream());
        //Parent root = FXMLLoader.load(c.getResource(fxml));

        mainController = fxmlLoader.getController();

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
