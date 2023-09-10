package results.simulations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import option4.PastSimulationInfoDTO;
import results.ResultsController;
import results.simulationDetails.SimulationDetailsController;
import results.simulations.listener.ShowButtonListener;
import results.simulations.simulationID.SimulationIDController;

import java.io.IOException;

public class SimulationsController {

    @FXML
    private VBox simulationHbox;
    private ResultsController resultsController;

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public void addSimulation(PastSimulationInfoDTO pastSimulationInfoDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulations/simulationID/SimulationID.fxml"));
            Parent simulationData = loader.load();
            SimulationIDController simulationIDController = loader.getController();
            simulationIDController.setShowButtonListener(resultsController);
            simulationIDController.setSimulationID(pastSimulationInfoDTO.getIdNum().toString());
            simulationHbox.getChildren().add(simulationData);

        } catch (IOException e) {
        }
    }
}