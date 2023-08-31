package details.selectedComponent.rule.action.replace;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.ProximityDTO;
import option2.ActionDTO.ReplaceDTO;

public class ReplaceDataController {

    @FXML
    private Label createEntityName;

    @FXML
    private Label killEntityName;

    @FXML
    private Label mode;

    public void SetData(ReplaceDTO replaceDTO) {
        mode.setText(replaceDTO.getMode());
        killEntityName.setText(replaceDTO.getMainEntityName());

        if(replaceDTO.getSecondaryEntityName() == null) {
            createEntityName.setText("None");
        }
        else {
            createEntityName.setText(replaceDTO.getSecondaryEntityName());
        }
    }
}