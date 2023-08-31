package details.selectedComponent.rule.action.increase;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.DecreaseDTO;
import option2.ActionDTO.IncreaseDTO;

public class IncreaseDataController {

    @FXML
    private Label by;

    @FXML
    private Label mainEntityName;

    @FXML
    private Label property;

    @FXML
    private Label secondEntityName;

    public void SetData(IncreaseDTO increaseDTO) {
        by.setText(increaseDTO.getBy());
        property.setText(increaseDTO.getProperty());
        mainEntityName.setText(increaseDTO.getMainEntityName());

        if(increaseDTO.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(increaseDTO.getSecondaryEntityName());
        }
    }

}