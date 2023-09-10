package results.simulationDetails;

import ex2DTO.EntityCountDTO;
import ex2DTO.SimulationDetailsDTO;
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

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValues() {
        setEntities();

        Thread thread = new Thread(() -> {
            Boolean stop = false;

            //todo how we stop the thread
            while (!stop) {
                try {
                    SimulationDetailsDTO simulationDetailsDTO = predictionManager.simulationDetailsDTO(id);
                    int i = 0;
                    for(EntityDetailsController entityDetailsController: entityDetailsControllers){
                        entityDetailsController.setEntityCount(simulationDetailsDTO.getEntityCountDTOList().get(i).getCount().toString());
                        i++;
                    }
                    terminationsDetailsController.setSecondsCount(simulationDetailsDTO.getTerminationDTO().getSeconds().toString());
                    terminationsDetailsController.setTicksCount(simulationDetailsDTO.getTerminationDTO().getTicks().toString());

                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        });

        thread.start();
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
}
