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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //FxmlController.load(getClass(), "../../view/MainView.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(Objects.requireNonNull(getClass().getResource("../../view/MainView.fxml")).openStream());
        //Parent root = FXMLLoader.load(c.getResource(fxml));

        MainViewController mainViewController = fxmlLoader.getController();

        primaryStage.setTitle("DiamondCircle");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
