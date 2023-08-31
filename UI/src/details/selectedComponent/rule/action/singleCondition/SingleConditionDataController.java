package details.selectedComponent.rule.action.singleCondition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.ActionDTO.MultipleConditionDTO;
import option2.ActionDTO.SingleConditionDTO;

public class SingleConditionDataController {

    @FXML
    private Label mainEntityName;

    @FXML
    private Label operator;

    @FXML
    private Label property;

    @FXML
    private Label secondEntityName;

    @FXML
    private Label value;

    @FXML
    private Label elseCount;

    @FXML
    private Label thisCount;

    public void SetData(SingleConditionDTO singleConditionDTO) {
        elseCount.setText(Integer.toString(singleConditionDTO.getElseActionAmount()));
        operator.setText(singleConditionDTO.getOperator());
        mainEntityName.setText(singleConditionDTO.getMainEntityName());
        thisCount.setText(Integer.toString(singleConditionDTO.getThisActionAmount()));
        property.setText(singleConditionDTO.getProperty());
        value.setText(singleConditionDTO.getValue());

        if(singleConditionDTO.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(singleConditionDTO.getSecondaryEntityName());
        }
    }


}