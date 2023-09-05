package managerFX;

import details.DetailsScreenController;
import header.PredictionHeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import manager.PredictionManager;
import newExecution.NewExecutionController;
import option2.SimulationDefinitionDTO;
import option3.EnvironmentDefinitionListDTO;

import java.io.IOException;

public class MainScreenController {
    private PredictionManager predictionManager = new PredictionManager();
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
       headerController.setPredictionManager(predictionManager);
    }

    public void setDetailsSet(Boolean detailsSet) {
        isDetailsSet = detailsSet;
    }

    public void loadDetailsScreen() {
        SimulationDefinitionDTO simulationDefinitionDTO = predictionManager.showCurrentSimulationData();
        try {
            if(!isDetailsSet) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/detailsScreen.fxml"));
                Parent detailsContent = loader.load();
                detailsController = loader.getController();

                detailsController.setMainScreenController(this);
                detailsController.initializeDetailsData(simulationDefinitionDTO);
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

            newExecutionController.setEntitiesData(predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList(),
                    predictionManager.showCurrentSimulationData().getGridCols() * predictionManager.showCurrentSimulationData().getGridRows());
            newExecutionController.setPredictionManager(predictionManager);
            newExecutionController.setMainScreenController(this);

            mainBorderPane.setCenter(newExecutionContent);
            isDetailsSet = false;

            EnvironmentDefinitionListDTO environmentDefinitionListDTO = predictionManager.runSimulationStep1();
            newExecutionController.setEnvironmentData(environmentDefinitionListDTO);

        } catch (IOException e) {
        }
    }

}
