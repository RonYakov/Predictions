package managerFX;

import details.DetailsScreenController;
import header.PredictionHeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import newExecution.NewExecutionController;

import java.io.IOException;

public class MainScreenController {
    @FXML
    private GridPane header;
    @FXML
    private PredictionHeaderController headerController;
    @FXML
    private DetailsScreenController detailsController;
    @FXML
    private NewExecutionController newExecutionController;
    @FXML
    private BorderPane mainBorderPane;
    private Boolean isDetailsSet = false;

    @FXML
    public void initialize() {
       headerController.setMainScreenController(this);
    }

    public void setDetailsSet(Boolean detailsSet) {
        isDetailsSet = detailsSet;
    }

    public void loadDetailsScreen() {
        try {
            if(!isDetailsSet) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/detailsScreen.fxml"));
                Parent detailsContent = loader.load();
                detailsController = loader.getController();

                detailsController.setMainScreenController(this);

                mainBorderPane.setCenter(detailsContent);
                isDetailsSet = true;
            }
        } catch (IOException e) {
        }
    }

    public void newExecutionScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/NewExecution.fxml"));
            Parent newExecutionContent = loader.load();
            newExecutionController = loader.getController();

            newExecutionController.setMainScreenController(this);

            mainBorderPane.setCenter(newExecutionContent);
            isDetailsSet = false;

        } catch (IOException e) {
        }
    }

}
