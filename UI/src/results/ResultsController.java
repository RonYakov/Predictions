package results;

import ex2DTO.SimulationIDDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import manager.PredictionManager;
import managerFX.MainScreenController;
import option4.PastSimulationInfoDTO;
import results.simulationDetails.SimulationDetailsController;
import results.simulationResult.SimulationResultController;
import results.simulations.SimulationsController;
import results.simulations.listener.ShowButtonListener;
import results.simulations.simulationID.SimulationIDController;

import java.io.IOException;
import java.util.List;

public class ResultsController implements ShowButtonListener {

    @FXML
    private Pane simulationDetails;
    @FXML
    private Pane simulationResult;
    @FXML
    private ScrollPane simulationsList;
    @FXML
    private SimulationsController simulationsListController;
    private MainScreenController mainScreenController;
    private PredictionManager predictionManager;
    private SimulationDetailsController simulationDetailsController;

    @FXML
    public void initialize() {
        simulationsListController.setResultsController(this);
        simulationsListController.manageSimulationsState();
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
        this.simulationsListController.setPredictionManager(predictionManager);
    }
    public void setSimulationsList() {
        List<PastSimulationInfoDTO> pastSimulationInfoDTOList = predictionManager.createPastSimulationInfoDTOList();

        for(PastSimulationInfoDTO pastSimulationInfoDTO : pastSimulationInfoDTOList) {
            simulationsListController.addSimulation(pastSimulationInfoDTO);
        }
    }

    @Override
    public void onShowClicked(Integer id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationDetails/SimulationDetails.fxml"));
            Parent simulationData = loader.load();
            SimulationDetailsController simulationDetailsController = loader.getController();
            simulationDetailsController.setId(id);
            simulationDetailsController.setPredictionManager(predictionManager);
            simulationDetailsController.setValues();

            if(this.simulationDetailsController != null) {
                this.simulationDetailsController.stopThread();
                simulationDetails.getChildren().clear();
            }

            if(predictionManager.getSimulationState(new SimulationIDDTO(id)).getState().equals("STOPPED") ||
                    predictionManager.getSimulationState(new SimulationIDDTO(id)).getState().equals("FAILED")) {
                showResult(id);
            }

            this.simulationDetailsController = simulationDetailsController;
            mainScreenController.setSimulationDetailsController(simulationDetailsController);
            simulationDetails.getChildren().add(simulationData);
        } catch (IOException e) {
        }
    }
    public void tryToShowResult(Integer id) {
        if(simulationDetailsController != null) {
            if(simulationDetailsController.getId().equals(id)) {
                showResult(id);
            }
        }
    }
    public void showResult(Integer id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/SimulationResult.fxml"));
            Parent simulationData = loader.load();
            SimulationResultController simulationResultController = loader.getController();

            simulationResult.getChildren().add(simulationData);
            simulationResultController.setId(id);
            simulationResultController.setPredictionManager(predictionManager);

        } catch (IOException e) {
        }
    }
}