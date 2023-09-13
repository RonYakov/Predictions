package results.simulations;

import ex2DTO.StopCauseReqDTO;
import ex2DTO.StopCauseResDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import manager.PredictionManager;
import option4.PastSimulationInfoDTO;
import results.ResultsController;
import results.simulationDetails.entityDetails.EntityDetailsController;
import results.simulations.simulationID.ListAction;
import results.simulations.simulationID.SimulationIDController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationsController {

    @FXML
    private VBox simulationHbox;
    private ResultsController resultsController;
    private PredictionManager predictionManager;
    private Thread thread;

    private List<SimulationIDController> simulationIDControllerList = new ArrayList<>();

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    public void manageSimulationsState() {
        thread = new Thread(() -> {
            while (true) {
                try {
                    if(simulationsListMethods(ListAction.EMPTY, null) != null) {
                        SimulationIDController simulationIDControllerStopped = simulationsListMethods(ListAction.SEARCH, null);

                        while (simulationIDControllerStopped != null) {
                            SimulationIDController finalSimulationIDControllerStopped = simulationIDControllerStopped;
                            StopCauseResDTO stopCauseResDTO = predictionManager.stopCause(new StopCauseReqDTO(finalSimulationIDControllerStopped.getID()));
                            Integer id = simulationIDControllerStopped.getID();
                            if(stopCauseResDTO.getState().equals("STOPPED")) {
                                Platform.runLater( () -> {
                                    finalSimulationIDControllerStopped.simulationStopped();
                                    resultsController.tryToShowResult(id);
                                });
                            }
                            else {
                                Platform.runLater( () -> {
                                    finalSimulationIDControllerStopped.simulationFailed();
                                    resultsController.tryToShowResult(id);
                                });
                            }
                            simulationIDControllerStopped = simulationsListMethods(ListAction.SEARCH, null);
                        }
                    }

                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        });

        thread.start();
    }

    private synchronized SimulationIDController simulationsListMethods(ListAction listAction, SimulationIDController simulationIDController) {
        switch (listAction) {
            case ADD:
                simulationIDControllerList.add(simulationIDController);
                return null;
            case EMPTY:
                if (simulationIDControllerList.isEmpty()) {
                    return null;
                }
                else {
                    return simulationIDControllerList.get(0);
                }
            default:
                for (SimulationIDController simulationIDController1 : simulationIDControllerList) {
                    Integer searchID = simulationIDController1.getID();
                    if (predictionManager.simulationDetailsDTO(searchID).getSimulationState().equals("STOPPED") || predictionManager.simulationDetailsDTO(searchID).getSimulationState().equals("FAILED")) {
                        simulationIDControllerList.remove(simulationIDController1);
                        return simulationIDController1;
                    }
                }
                return null;
        }
    }

    public void addSimulation(PastSimulationInfoDTO pastSimulationInfoDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulations/simulationID/SimulationID.fxml"));
            Parent simulationData = loader.load();
            SimulationIDController simulationIDController = loader.getController();
            simulationIDController.setShowButtonListener(resultsController);
            simulationIDController.setSimulationID(pastSimulationInfoDTO.getIdNum().toString());

            simulationsListMethods(ListAction.ADD, simulationIDController);
            simulationHbox.getChildren().add(simulationData);

        } catch (IOException e) {
        }
    }
}