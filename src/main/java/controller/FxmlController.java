package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.util.Util;

import java.io.IOException;

import static java.util.Objects.*;

public class FxmlController {
    public static void load(Class c, String fxml) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        javafx.scene.Parent root = fxmlLoader.load(c.getResource(fxml).openStream());
        var controller = fxmlLoader.getController();

        Stage primaryStage = new Stage();
        //javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(c.getResource(fxml));
        primaryStage.setTitle("DiamondCircle");
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void loadDialog(Class c, String fxml) {
        try {
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            Pane myPane = FXMLLoader.load(requireNonNull(c.getResource(fxml)));
            Scene myScene = new Scene(myPane);
            s.setScene(myScene);
            s.showAndWait();
        } catch (IOException ex) {
            Util.logAsync(c, ex);
        }
    }
}
