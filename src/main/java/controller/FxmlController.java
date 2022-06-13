package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.*;

public class FxmlController {
    public static void load(Class c, String fxml) throws IOException {
        Stage primaryStage = new Stage();
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(c.getResource(fxml));
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
            Logger.getLogger(c.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
