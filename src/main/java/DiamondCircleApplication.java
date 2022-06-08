import javafx.application.Application;
import javafx.stage.Stage;

public class DiamondCircleApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        primaryStage.setTitle("DiamondCircle");
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
