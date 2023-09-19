package managerFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("predictions test");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerFX/mainScreen.fxml"));
        Parent root = loader.load();
        MainScreenController mainScreenController = loader.getController();
        Scene scene = new Scene(root, 1300, 700);
        mainScreenController.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
