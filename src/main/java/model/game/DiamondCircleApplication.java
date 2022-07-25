package model.game;

import controller.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DiamondCircleApplication extends Application {

    private static final String MAIN_VIEW_FXML = "./view/MainView.fxml";
    private static final String TITLE = "DiamondCircleGame";

    public static MainViewController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getClassLoader().getResource(MAIN_VIEW_FXML));
        Parent root = fxmlLoader.load();

        mainController = fxmlLoader.getController();

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1250);
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

    }
}
