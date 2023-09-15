package results.simulationResult.propAvg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PropAvgController {

    @FXML
    private Label propAvg;

    public void setPropAvg(String propAvg) {
        this.propAvg.setText(propAvg);
    }

}