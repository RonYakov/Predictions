package details.selectedComponent.rule.action.decrease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.CalculationDTO;
import option2.ActionDTO.DecreaseDTO;

public class DecreaseDataController {

    @FXML
    private Label by;

    @FXML
    private Label mainEntityName;

    @FXML
    private Label property;

    @FXML
    private Label secondEntityName;

    public void SetData(DecreaseDTO decreaseDTO) {
        by.setText(decreaseDTO.getBy());
        property.setText(decreaseDTO.getProperty());
        mainEntityName.setText(decreaseDTO.getMainEntityName());

        if(decreaseDTO.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(decreaseDTO.getSecondaryEntityName());
        }
    }

}