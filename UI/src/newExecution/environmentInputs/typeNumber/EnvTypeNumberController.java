package newExecution.environmentInputs.typeNumber;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.NewExecutionController;
import newExecution.listener.StartButtonClickedListener;
import option3.EnvironmentDefinitionDTO;

public class EnvTypeNumberController implements StartButtonClickedListener {

    @FXML
    private Label nameLabel;
    @FXML
    private Label rangeLabel;
    @FXML
    private Spinner<Double> userChoice;
    private NewExecutionController newExecutionController;

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
        newExecutionController.addListenerToStartButton(this);
    }

    @FXML
    public void initialize() {
    }

    @Override
    public void startOnClicked() {
    //    newExecutionController.addEnvironmentToList();
    }

    public void setData(EnvironmentDefinitionDTO environmentDefinitionDTO) {
        nameLabel.setText(environmentDefinitionDTO.getName());

        userChoice.setEditable(true);

        if(environmentDefinitionDTO.getRangeTo() != null) {
            rangeLabel.setText(environmentDefinitionDTO.getRangeFrom() + " - " + environmentDefinitionDTO.getRangeTo());
            SpinnerValueFactory<Double> range = new SpinnerValueFactory.DoubleSpinnerValueFactory(Double.parseDouble(environmentDefinitionDTO.getRangeFrom()),
                    Double.parseDouble(environmentDefinitionDTO.getRangeTo()), Double.parseDouble(environmentDefinitionDTO.getRangeFrom()));
            userChoice.setValueFactory(range);
        }
        else {
            rangeLabel.setText(" --- ");
        }
    }
}