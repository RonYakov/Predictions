package results.simulationDetails;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import ex2DTO.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import manager.PredictionManager;
import results.simulationDetails.entityDetails.EntityDetailsController;
import results.simulationDetails.terminationDetails.TerminationDetailsController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationDetailsController {

    @FXML
    private VBox entitiesDetails;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    @FXML
    private Button stopButton;
    @FXML
    private VBox terminationsDetails;
    @FXML
    private TerminationDetailsController terminationsDetailsController;
    private List<EntityDetailsController> entityDetailsControllers = new ArrayList<>();
    private PredictionManager predictionManager;
    private Integer id;
    private Thread thread;
    private Boolean stopSimulation;

    @FXML
    public void initialize() {
        resumeButton.setDisable(true);
    }
    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
        if(predictionManager.getSimulationState(new SimulationIDDTO(id)).getState().equals("STOPPED") ||
                predictionManager.getSimulationState(new SimulationIDDTO(id)).getState().equals("FAILED")){
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValues() {
        setEntities();
        stopSimulation = false;

        thread = new Thread(() -> {
            Boolean stop = false;

            String simulationState = "RUNNING";
            System.out.println(thread.getName());
            while (!stop && !simulationState.equals("STOPPED") && !simulationState.equals("FAILED")) {
                try {
                    SimulationDetailsDTO simulationDetailsDTO = predictionManager.simulationDetailsDTO(id);
                    Platform.runLater( () -> {
                        int i = 0;
                        for(EntityDetailsController entityDetailsController: entityDetailsControllers){
                            entityDetailsController.setEntityCount(simulationDetailsDTO.getEntityCountDTOList().get(i).getCount().toString());
                            i++;
                        }
                        terminationsDetailsController.setSecondsCount(simulationDetailsDTO.getTerminationDTO().getSeconds().toString());
                        terminationsDetailsController.setTicksCount(simulationDetailsDTO.getTerminationDTO().getTicks().toString());
                    });
                    simulationState = simulationDetailsDTO.getSimulationState();

                    if(stopSimulation) {
                        stop = true;
                        predictionManager.stopSimulation(new StopDTO(id));
                    }
                    Thread.sleep(200);

                } catch (InterruptedException ignore) {
                   stop = true;
                }
            }
            resumeButton.setDisable(true);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
        });
        thread.start();
    }

    public void stopThread() {
        if (thread != null) {
            System.out.println(thread.getName());
            thread.interrupt(); // Signal the thread to stop
        }
    }

    private void setEntities() {
        List<EntityCountDTO> entityCountDTOList = predictionManager.getEntitiesCountDTO(id);

        for(EntityCountDTO entityCountDTO : entityCountDTOList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationDetails/entityDetails/EntityDetails.fxml"));
                Parent entityData = loader.load();
                EntityDetailsController simulationDetailsController = loader.getController();

                simulationDetailsController.setEntityName(entityCountDTO.getName());
                simulationDetailsController.setEntityCount(entityCountDTO.getCount().toString());
                entityDetailsControllers.add(simulationDetailsController);
                entitiesDetails.getChildren().add(entityData);
            } catch (IOException e) {
            }
        }
    }

    @FXML
    void OnStopClicked(ActionEvent event){
        stopSimulation = true;
        simulationStop();
    }
    @FXML
    void OnPauseClicked(ActionEvent event){
        predictionManager.pauseSimulation(new PauseAndResumeSimulationDTO(id));
        simulationPause();
    }
    @FXML
    void OnResumeClicked(ActionEvent event){
        predictionManager.resumeSimulation(new PauseAndResumeSimulationDTO(id));
        pauseButton.setDisable(false);
        resumeButton.setDisable(true);
    }

    public void simulationStop() {
        resumeButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
    }
    public void simulationPause() {
        pauseButton.setDisable(true);
        resumeButton.setDisable(false);
    }

}
