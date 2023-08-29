package details.selectedComponent.rule;

import details.selectedComponent.rule.action.set.SetDataController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import option2.ActionDTO.*;
import option2.RulesDTO;

import java.util.List;

public class RulesDetailsController {

    @FXML
    private Pane actionDetails;
    @FXML
    private Label numberOfActions;
    @FXML
    private Label probability;
    @FXML
    private Label rulleName;
    @FXML
    private Label ticks;
    @FXML
    private TreeView<String> actionTreeView = new TreeView<>();

    private List<ActionDTO> actionDTOList;

    public void setAllDataMembers(RulesDTO rulesDTO) {
        numberOfActions.setText(rulesDTO.getActionCounter().toString());
        probability.setText(rulesDTO.getProbability().toString());
        rulleName.setText(rulesDTO.getName());
        ticks.setText(rulesDTO.getTicks().toString());
        setActionTreeView(rulesDTO.getActionTypes());
    }

    private void setActionTreeView(List<ActionDTO> actionTypes) {
        TreeItem<String> root = new TreeItem<>("Select Action");
        for (ActionDTO actionType : actionTypes) {
            root.getChildren().add(new TreeItem<>(actionType.getName()));
        }
        actionTreeView.setRoot(root);
        actionDTOList = actionTypes;
    }

    @FXML
    private void SelectActionClicked(ActionEvent event) {
        TreeItem<String> selectedItem = actionTreeView.getSelectionModel().getSelectedItem();
        int i = 0;
        for(TreeItem<String> treeItem : actionTreeView.getRoot().getChildren()) {
            if(treeItem.equals(selectedItem)) {
                break;
            }
            i++;
        }
        ActionDTO actionDTO = actionDTOList.get(i);
    }

    private void setActionDetailsPain(ActionDTO actionDTO) {
        switch (actionDTO.getName()) {
            case "INCREASE":
                createIncreaseAction((IncreaseDTO)actionDTO);
                break;
            case "DECREASE":
                createDecreaseAction((DecreaseDTO)actionDTO);
                break;
            case "MULTIPLY":
                createMultiplyAction((CalculationDTO)actionDTO);
                break;
            case "DIVIDE":
                createDivideAction((CalculationDTO)actionDTO);
                break;
            case "MULTIPLE-CONDITION":
                createMulConAction((MultipleConditionDTO)actionDTO);
                break;
            case "SINGLE-CONDITION":
                createSinConAction((SingleConditionDTO)actionDTO);
                break;
            case "KILL":
                createKillAction((KillDTO)actionDTO);
                break;
            case "SET":
                createSetAction((SetDTO)actionDTO);
                break;
        }
    }

    private void createIncreaseAction(IncreaseDTO actionDTO) {
        
    }

    private void createDecreaseAction(DecreaseDTO actionDTO) {

    }

    private void createMultiplyAction(CalculationDTO actionDTO) {

    }

    private void createDivideAction(CalculationDTO actionDTO) {

    }

    private void createMulConAction(MultipleConditionDTO actionDTO) {

    }

    private void createSinConAction(SingleConditionDTO actionDTO) {

    }

    private void createKillAction(KillDTO actionDTO) {

    }

    private void createSetAction(SetDTO actionDTO) {

    }

}