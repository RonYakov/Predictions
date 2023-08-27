package details.selectedComponent.termination;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.TerminationDTO;

public class TerminationDetailsController {

    @FXML
    private Label seconds;
    @FXML
    private Label ticks;

    public void setAllDataMembers(TerminationDTO terminationDTO) {
        if(terminationDTO.getSeconds() != null) {
            seconds.setText(terminationDTO.getSeconds().toString());
        }
        else {
            seconds.setText("---");
        }
        if(terminationDTO.getTicks() != null) {
            ticks.setText(terminationDTO.getTicks().toString());
        }
        else {
            ticks.setText("---");
        }
    }

}