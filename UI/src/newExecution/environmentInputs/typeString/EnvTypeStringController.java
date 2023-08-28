package newExecution.environmentInputs.typeString;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newExecution.NewExecutionController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionDTO;

public class EnvTypeStringController implements StartButtonClickedListener {

    @FXML
    private Label nameLabel;
    @FXML
    private TextField userInput;
    private NewExecutionController newExecutionController;

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
        newExecutionController.addListenerToStartButton(this);
    }

    @Override
    public void startOnClicked() {
        // todo need to create the relevant DTO and shoot it back to the newExeController
    }

    @FXML
    private void userInputClicked(ActionEvent event) {

    }

    public void setData(EnvironmentDefinitionDTO environmentDefinitionDTO) {
        nameLabel.setText(environmentDefinitionDTO.getName());
    }

}
