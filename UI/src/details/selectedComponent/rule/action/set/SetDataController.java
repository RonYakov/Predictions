package details.selectedComponent.rule.action.set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.KillDTO;
import option2.ActionDTO.SetDTO;

public class SetDataController {

    @FXML
    private Label mainEntityName;

    @FXML
    private Label property;

    @FXML
    private Label secondEntityName;

    @FXML
    private Label value;

    public void SetData(SetDTO setDTO) {
        mainEntityName.setText(setDTO.getMainEntityName());
        property.setText(setDTO.getProperty());
        value.setText(setDTO.getValue());

        if(setDTO.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(setDTO.getSecondaryEntityName());
        }
    }

}
