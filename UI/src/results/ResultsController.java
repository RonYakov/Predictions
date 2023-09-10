package results;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import manager.PredictionManager;
import managerFX.MainScreenController;
import option4.PastSimulationInfoDTO;
import results.simulationDetails.SimulationDetailsController;
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

    @FXML
    public void initialize() {
        simulationsListController.setResultsController(this);
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
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
            simulationDetails.getChildren().add(simulationData);
        } catch (IOException e) {
        }
    }
}