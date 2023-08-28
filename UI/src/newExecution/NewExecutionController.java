package newExecution;

import header.PredictionHeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import managerFX.MainScreenController;
import newExecution.environmentInputs.EnvironmentInputsController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionListDTO;
import option3.EnvironmentInitDTO;
import option3.EnvironmentInitListDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewExecutionController {
    @FXML
    private BorderPane environmentInputs;
    @FXML
    private EnvironmentInputsController environmentInputsController;
    @FXML
    private Button clearButton;
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Button startButton;
    private MainScreenController mainScreenController;
    private Double originalDividerPosition;
    private List<StartButtonClickedListener> startListener;
    private List<EnvironmentInitDTO> environmentInitDTOList;


    @FXML
    public void initialize() {
        startListener = new LinkedList<>();
        originalDividerPosition = 0.41; // Store your original value here
        setDivider();
        environmentInputsController.setNewExecutionController(this);
        environmentInitDTOList = new ArrayList<>();
    }

    private void setDivider(){
        mainSplitPane.setDividerPositions(originalDividerPosition);

        // Add a listener to the divider position
        mainSplitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            // Reset the position to the original value
            mainSplitPane.setDividerPositions(originalDividerPosition);
        });
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

    @FXML
    private void clearButtonClicked(ActionEvent event) {

    }

    @FXML
    private void startButtonClicked(ActionEvent event) {
        environmentInitDTOList.clear();
        for(StartButtonClickedListener listener : startListener) {
            listener.startOnClicked();
        }
    }

}
