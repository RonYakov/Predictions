package newExecution.environmentInputs.typeString;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newExecution.NewExecutionController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionDTO;
import option3.EnvironmentInitDTO;

import java.util.Random;

public class EnvTypeStringController implements StartButtonClickedListener {

    @FXML
    private Label nameLabel;
    @FXML
    private TextField userInput;
    private NewExecutionController newExecutionController;
    private boolean firstClick = true;
    private Boolean isValueSet = false;

    @FXML
    private void initialize() {
        userInput.setOnMouseClicked(e -> {
            if (firstClick) {
                userInput.clear();
                firstClick = false;
            }
        });

        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
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
            value = userInput.getText();
        } else {
            String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!?,_-.() ";
            int length = new Random().nextInt(50) + 1;
            StringBuilder randomString = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int randomIndex = new Random().nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                randomString.append(randomChar);
            }
            value = randomString.toString();
        }
        newExecutionController.addEnvironmentToList(new EnvironmentInitDTO(nameLabel.getText(), value));
    }

    @FXML
    private void userInputClicked(ActionEvent event) {

    }

    public void setData(EnvironmentDefinitionDTO environmentDefinitionDTO) {
        nameLabel.setText(environmentDefinitionDTO.getName());
    }

}
