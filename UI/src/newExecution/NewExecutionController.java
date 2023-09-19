package newExecution;

import ex2DTO.SimulationIDDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import manager.PredictionManager;
import managerFX.MainScreenController;
import newExecution.entitiesPopulation.EntityPopulationController;
import newExecution.environmentInputs.EnvironmentInputsController;
import newExecution.listener.ClearButtonClickedListener;
import newExecution.listener.RerunButtonClickedListener;
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
    @FXML
    private BorderPane mainBorderPain;
    private MainScreenController mainScreenController;
    private Double originalDividerPosition;
    private List<StartButtonClickedListener> startListener;
    private List<ClearButtonClickedListener> clearListener;
    private List<EnvironmentInitDTO> environmentInitDTOList= new ArrayList<>();
    private List<EntityPopulationDTO>entityPopulationDTOList = new ArrayList<>();
    private List<RerunButtonClickedListener> rerunButtonClickedEntitiesListeners= new ArrayList<>();
    private List<RerunButtonClickedListener> rerunButtonClickedEnvironmentsListeners = new ArrayList<>();
    private PredictionManager predictionManager;

    @FXML
    public void initialize() {
        startListener = new LinkedList<>();
        clearListener = new LinkedList<>();
        originalDividerPosition = 0.4199; // Store your original value here
        setDivider();
        environmentInputsController.setNewExecutionController(this);
        environmentInitDTOList = new ArrayList<>();
        entityPopulationController.setNewExecutionController(this);
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    public void addRerunEntityListener(RerunButtonClickedListener rerunButtonClickedListener) {
        rerunButtonClickedEntitiesListeners.add(rerunButtonClickedListener);
    }
    public void addRerunEnvironmentListener(RerunButtonClickedListener rerunButtonClickedListener) {
        rerunButtonClickedEnvironmentsListeners.add(rerunButtonClickedListener);
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

    public void onRerun(Integer id) {
        List<EnvironmentInitDTO> environmentInitDTOListForRerun = predictionManager.getEnvironmentRerun(new SimulationIDDTO(id));
        List<EntityPopulationDTO> entityPopulationDTOListForRerun = predictionManager.getEntityRerun(new SimulationIDDTO(id));
        int i = 0;

        for (EnvironmentInitDTO environmentInitDTO : environmentInitDTOListForRerun) {
            rerunButtonClickedEnvironmentsListeners.get(i).onRerun(environmentInitDTO.getNewValue());
            i++;
        }
        i = 0;
        for (EntityPopulationDTO entityPopulationDTO : entityPopulationDTOListForRerun) {
            rerunButtonClickedEntitiesListeners.get(i).onRerun(entityPopulationDTO.getCount().toString());
            i++;
        }
    }

    public void setOnColorChange(String color) {
        mainBorderPain.setStyle("-fx-background-color: " + color + ";");
        mainSplitPane.setStyle("-fx-background-color: " + color + ";");
        entityPopulation.setStyle("-fx-background-color: " + color + ";");
        environmentInputs.setStyle("-fx-background-color: " + color + ";");
        entityPopulation.getCenter().setStyle("-fx-background-color: " + color + ";");
        environmentInputs.getCenter().setStyle("-fx-background-color: " + color + ";");

        entityPopulationController.setOnColorChange(color);
        environmentInputsController.setOnColorChange(color);

        String newBackgroundColor;
        switch(color) {
            case "#EDF0F0":
                newBackgroundColor = "-fx-background-color: #4CAF50;";
                clearButton.setStyle(newBackgroundColor);
                startButton.setStyle(newBackgroundColor);
                break;
            case "#D4E6F1":
                newBackgroundColor = "-fx-background-color: #F3B180;";
                clearButton.setStyle(newBackgroundColor);
                startButton.setStyle(newBackgroundColor);
                break;
            case "#EBDEF0":
                newBackgroundColor = "-fx-background-color: #BB98F4;";
                clearButton.setStyle(newBackgroundColor);
                startButton.setStyle(newBackgroundColor);
        }
    }
}
