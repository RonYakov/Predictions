package details.selectedComponent.rule.action.multipleCondition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.IncreaseDTO;
import option2.ActionDTO.MultipleConditionDTO;

public class MultipleConditionDataController {


    @FXML
    private Label elseCount;

    @FXML
    private Label logical;

    @FXML
    private Label mainEntityName;

    @FXML
    private Label secondEntityName;

    @FXML
    private Label thisCount;

    public void SetData(MultipleConditionDTO multipleConditionDTO) {
        elseCount.setText(Integer.toString(multipleConditionDTO.getElseActionAmount()));
        logical.setText(multipleConditionDTO.getLogic());
        mainEntityName.setText(multipleConditionDTO.getMainEntityName());
        thisCount.setText(Integer.toString(multipleConditionDTO.getThisActionAmount()));

        if(multipleConditionDTO.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(multipleConditionDTO.getSecondaryEntityName());
        }
    }

}
