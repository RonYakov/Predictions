package details.selectedComponent.rule;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import option2.RulesDTO;

import java.util.List;

public class RulesDetailsController {

    @FXML
    private VBox actionVbox;
    @FXML
    private Label numberOfActions;
    @FXML
    private Label probability;
    @FXML
    private Label rulleName;
    @FXML
    private Label ticks;

    public void setAllDataMembers(RulesDTO rulesDTO) {
        numberOfActions.setText(rulesDTO.getActionCounter().toString());
        probability.setText(rulesDTO.getProbability().toString());
        rulleName.setText(rulesDTO.getName());
        ticks.setText(rulesDTO.getTicks().toString());
        setActionBox(rulesDTO.getActionTypes());
    }

    private void setActionBox(List<String> actionTypes) {
        for (String actionType : actionTypes) {
            //todo add with component!!
        }
    }
}