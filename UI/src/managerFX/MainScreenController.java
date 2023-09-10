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
import results.ResultsController;

import java.io.IOException;

public class MainScreenController {
    private PredictionManager predictionManager = new PredictionManager();
    @FXML
    private GridPane header;
    @FXML
    private PredictionHeaderController headerController;
    private DetailsScreenController detailsController;
    private NewExecutionController newExecutionController;
    private ResultsController resultsController;
    @FXML
    private BorderPane mainBorderPane;
    private Boolean isDetailsPresent = false;
    private Boolean isNewExecutionPresent = false;
    private Boolean isResultsPresent = false;

    @FXML
    public void initialize() {
       headerController.setMainScreenController(this);
       headerController.setPredictionManager(predictionManager);
    }

    public void loadDetailsScreen() {
        SimulationDefinitionDTO simulationDefinitionDTO = predictionManager.showCurrentSimulationData();
        try {
            if(!isDetailsPresent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/detailsScreen.fxml"));
                Parent detailsContent = loader.load();
                detailsController = loader.getController();

                detailsController.setMainScreenController(this);
                detailsController.initializeDetailsData(simulationDefinitionDTO);
                mainBorderPane.setCenter(detailsContent);
                isDetailsPresent = true;
                isResultsPresent = false;
                isNewExecutionPresent = false;
            }
        } catch (IOException e) {
        }
    }

    public void newExecutionScreen() {
        try {
            if (!isNewExecutionPresent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/NewExecution.fxml"));
                Parent newExecutionContent = loader.load();
                newExecutionController = loader.getController();

                newExecutionController.setEntitiesData(predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList(),
                        predictionManager.showCurrentSimulationData().getGridCols() * predictionManager.showCurrentSimulationData().getGridRows());
                newExecutionController.setPredictionManager(predictionManager);
                newExecutionController.setMainScreenController(this);

                mainBorderPane.setCenter(newExecutionContent);

                EnvironmentDefinitionListDTO environmentDefinitionListDTO = predictionManager.runSimulationStep1();
                newExecutionController.setEnvironmentData(environmentDefinitionListDTO);
                isNewExecutionPresent = true;
                isDetailsPresent = false;
                isResultsPresent = false;
            }
        }catch(IOException e){
        }
    }

    public void resultsScreen() {
        try {
            if(!isResultsPresent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/Results.fxml"));
                Parent resultsData = loader.load();
                resultsController = loader.getController();

                mainBorderPane.setCenter(resultsData);
                resultsController.setMainScreenController(this);
                resultsController.setPredictionManager(predictionManager);
                resultsController.setSimulationsList();
                isResultsPresent = true;
                isDetailsPresent = false;
                isNewExecutionPresent = false;
            }
        } catch (IOException e) {
        }
    }
}
