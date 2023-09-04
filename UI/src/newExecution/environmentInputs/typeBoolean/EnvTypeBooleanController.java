package newExecution.environmentInputs.typeBoolean;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import newExecution.NewExecutionController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionDTO;
import option3.EnvironmentInitDTO;

import java.util.Random;

public class EnvTypeBooleanController implements StartButtonClickedListener {

    @FXML
    private Label nameLabel;
    @FXML
    private ComboBox<String> userChoice;
    private NewExecutionController newExecutionController;
    private Boolean isValueSet = false;


    @FXML
    private void initialize() {
        userChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                isValueSet = true;
            }
        });
    }
    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
        newExecutionController.addListenerToStartButton(this);
    }

    @Override
    public void startOnClicked() {
        String value;
        if(isValueSet) {
            value = userChoice.getValue();
        } else {
            Random random = new Random();
            Boolean randomBoolean = random.nextBoolean();
            value = randomBoolean.toString();
        }

        newExecutionController.addEnvironmentToList(new EnvironmentInitDTO(nameLabel.getText(), value));
    }

    public void setData(EnvironmentDefinitionDTO environmentDefinitionDTO) {
        nameLabel.setText(environmentDefinitionDTO.getName());
    }
}