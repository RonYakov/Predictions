package details.selectedComponent.rule;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import option2.ActionDTO.ActionDTO;
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
    @FXML
    private TreeView<String> actionTreeView = new TreeView<>();

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
    }
}