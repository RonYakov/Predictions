package results.simulationDetails.terminationDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TerminationDetailsController {

    @FXML
    private Label secondsCount;

    @FXML
    private Label ticksCount;

    public void setSecondsCount(String secondsCount) {
        this.secondsCount.setText(secondsCount);
    }

    public void setTicksCount(String ticksCount) {
        this.ticksCount.setText(ticksCount);
    }
}