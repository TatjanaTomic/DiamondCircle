package model.game;

import controller.FxmlController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DiamondCircleApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FxmlController.load(getClass(), "../../view/MainView.fxml");
    }
}
