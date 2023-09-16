package results.simulationResult.failedCause;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FailedCauseController {

    @FXML
    private Label simulationStopCause;

    public void setSimulationStopCause(String simulationStopCause) {
        this.simulationStopCause.setText(simulationStopCause);
    }
}