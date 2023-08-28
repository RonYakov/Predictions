package newExecution.environmentInputs.typeBoolean;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import newExecution.NewExecutionController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionDTO;

public class EnvTypeBooleanController implements StartButtonClickedListener {

    @FXML
    private Label nameLabel;
    @FXML
    private ComboBox<?> userChoice;
    private NewExecutionController newExecutionController;

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
        newExecutionController.addListenerToStartButton(this);
    }

    @Override
    public void startOnClicked() {
        // todo need to create the relevant DTO and shoot it back to the newExeController
    }

    public void setData(EnvironmentDefinitionDTO environmentDefinitionDTO) {
        nameLabel.setText(environmentDefinitionDTO.getName());
    }
}