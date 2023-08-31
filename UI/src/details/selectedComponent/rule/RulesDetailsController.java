package details.selectedComponent.rule;

import details.selectedComponent.rule.action.calculation.CalculationDataController;
import details.selectedComponent.rule.action.decrease.DecreaseDataController;
import details.selectedComponent.rule.action.increase.IncreaseDataController;
import details.selectedComponent.rule.action.kill.KillDataController;
import details.selectedComponent.rule.action.multipleCondition.MultipleConditionDataController;
import details.selectedComponent.rule.action.set.SetDataController;
import details.selectedComponent.rule.action.singleCondition.SingleConditionDataController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import option2.ActionDTO.*;
import option2.RulesDTO;

import java.io.IOException;
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
        setActionDetailsPain(actionDTO);
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
                createCalculationAction((CalculationDTO)actionDTO);
                break;
            case "DIVIDE":
                createCalculationAction((CalculationDTO)actionDTO);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/increase/IncreaseData.fxml"));
            Parent increaseContent = loader.load();
            IncreaseDataController increaseDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(increaseContent);

        } catch (IOException e) {
        }
    }

    private void createDecreaseAction(DecreaseDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/decrease/DecreaseData.fxml"));
            Parent decreaseContent = loader.load();
            DecreaseDataController decreaseDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(decreaseContent);

        } catch (IOException e) {
        }
    }

    private void createCalculationAction(CalculationDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/calculation/CalculationData.fxml"));
            Parent calculationContent = loader.load();
            CalculationDataController calculationDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(calculationContent);

        } catch (IOException e) {
        }
    }

    private void createMulConAction(MultipleConditionDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/multipleCondition/MultipleConditionData.fxml"));
            Parent multipleConditionContent = loader.load();
            MultipleConditionDataController multipleConditionDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(multipleConditionContent);

        } catch (IOException e) {
        }
    }

    private void createSinConAction(SingleConditionDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/singleCondition/SingleConditionData.fxml"));
            Parent singleConditionContent = loader.load();
            SingleConditionDataController singleConditionDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(singleConditionContent);

        } catch (IOException e) {
        }
    }

    private void createKillAction(KillDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/kill/KillData.fxml"));
            Parent killContent = loader.load();
            KillDataController killDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(killContent);

        } catch (IOException e) {
        }
    }

    private void createSetAction(SetDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/action/set/SetData.fxml"));
            Parent setContent = loader.load();
            SetDataController setDataController = loader.getController();

            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(setContent);

        } catch (IOException e) {
        }
    }

}