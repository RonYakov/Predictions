package details.selectedComponent.rule.action.proximity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.MultipleConditionDTO;
import option2.ActionDTO.ProximityDTO;

public class ProximityDataController {

    @FXML
    private Label actionCount;

    @FXML
    private Label environmentDepth;

    @FXML
    private Label sourceEntityName;

    @FXML
    private Label targetEntityName;

    public void SetData(ProximityDTO proximityDTO) {
        actionCount.setText(proximityDTO.getNumOfActionsForMeetsBetweenEntities());
        environmentDepth.setText(proximityDTO.getDepthOfEnvironment());
        sourceEntityName.setText(proximityDTO.getMainEntityName());

        if(proximityDTO.getSecondaryEntityName() == null) {
            targetEntityName.setText("None");
        }
        else {
            targetEntityName.setText(proximityDTO.getSecondaryEntityName());
        }
    }


}
