package results.simulations.simulationID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import results.simulations.listener.ShowButtonListener;

public class SimulationIDController {

    @FXML
    private Button showButton;

    @FXML
    private Label simulationID;
    private ShowButtonListener showButtonListener;

    public void setSimulationID(String simulationID) {
        this.simulationID.setText(simulationID);
    }

    public void setShowButtonListener(ShowButtonListener showButtonListener) {
        this.showButtonListener = showButtonListener;
    }

    public void setShowButton(Button showButton) {
        this.showButton = showButton;
    }

    @FXML
    public void showButtonClicked(ActionEvent event) {
        showButtonListener.onShowClicked(Integer.parseInt(simulationID.getText()));
    }
}