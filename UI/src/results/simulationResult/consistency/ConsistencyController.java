package results.simulationResult.consistency;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConsistencyController {

    @FXML
    private Label consistencyAverage;

    public void setConsistencyAverage(String consistencyAverage) {
        this.consistencyAverage.setText(consistencyAverage);
    }
}