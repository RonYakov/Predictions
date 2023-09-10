package newExecution;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import manager.PredictionManager;
import managerFX.MainScreenController;
import newExecution.entitiesPopulation.EntityPopulationController;
import newExecution.environmentInputs.EnvironmentInputsController;
import newExecution.listener.ClearButtonClickedListener;
import newExecution.listener.StartButtonClickedListener;
import option2.EntityDefinitionDTO;
import option3.EntityPopulationDTO;
import option3.EnvironmentDefinitionListDTO;
import option3.EnvironmentInitDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewExecutionController {
    @FXML
    private BorderPane environmentInputs;
    @FXML
    private EnvironmentInputsController environmentInputsController;

    @FXML
    private BorderPane entityPopulation;
    @FXML
    private EntityPopulationController entityPopulationController;
    @FXML
    private Button clearButton;
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Button startButton;
    private MainScreenController mainScreenController;
    private Double originalDividerPosition;
    private List<StartButtonClickedListener> startListener;
    private List<ClearButtonClickedListener> clearListener;
    private List<EnvironmentInitDTO> environmentInitDTOList= new ArrayList<>();
    private List<EntityPopulationDTO>entityPopulationDTOList = new ArrayList<>();
    private PredictionManager predictionManager;



    @FXML
    public void initialize() {
        startListener = new LinkedList<>();
        clearListener = new LinkedList<>();
        originalDividerPosition = 0.41; // Store your original value here
        setDivider();
        environmentInputsController.setNewExecutionController(this);
        environmentInitDTOList = new ArrayList<>();
        entityPopulationController.setNewExecutionController(this);
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    private void setDivider(){
        mainSplitPane.setDividerPositions(originalDividerPosition);

        // Add a listener to the divider position
        mainSplitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            // Reset the position to the original value
            mainSplitPane.setDividerPositions(originalDividerPosition);
        });
    }

    public void setEntitiesData(List<EntityDefinitionDTO> entityDefinitionDTOList, int gridSize) {
        entityPopulationController.setMaxCountLable(gridSize);
        entityPopulationController.setEntities(entityDefinitionDTOList);
    }

    public void addToEntityPopulationDTOList(EntityPopulationDTO entityPopulationDTO) {
        entityPopulationDTOList.add(entityPopulationDTO);
    }
    public void addEnvironmentToList(EnvironmentInitDTO environmentInitDTO) {
        environmentInitDTOList.add(environmentInitDTO);
    }

    public void setEnvironmentData(EnvironmentDefinitionListDTO environmentDefinitionListDTO) {
        environmentInputsController.setEnvironmentData(environmentDefinitionListDTO);
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void addListenerToStartButton(StartButtonClickedListener listener) {
        startListener.add(listener);
    }
    public void addListenerToClearButton(ClearButtonClickedListener listener) {
        clearListener.add(listener);
    }

    @FXML
    private void clearButtonClicked(ActionEvent event) {
        for(ClearButtonClickedListener listener : clearListener) {
            listener.clearOnClicked();
        }

        entityPopulationController.initialize();
        setEntitiesData(predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList(),
                predictionManager.showCurrentSimulationData().getGridCols() * predictionManager.showCurrentSimulationData().getGridRows());
    }

    @FXML
    private void startButtonClicked(ActionEvent event) {
        environmentInitDTOList.clear();
        for(StartButtonClickedListener listener : startListener) {
            listener.startOnClicked();
        }

        predictionManager.runSimulationStep2(environmentInitDTOList, entityPopulationDTOList);
        mainScreenController.resultsScreen();
    }

}
