package newExecution.entitiesPopulation.entityCount;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.NewExecutionController;
import newExecution.entitiesPopulation.EntityPopulationController;
import newExecution.listener.ClearButtonClickedListener;
import newExecution.listener.StartButtonClickedListener;
import option2.EntityDefinitionDTO;
import option3.EntityPopulationDTO;

public class EntityCountController implements PopulationCountListener, StartButtonClickedListener {

    @FXML
    private Spinner<Integer> count;
    @FXML
    private Label name;
    private Integer currMaxSize;
    private Integer originalMaxSize;
    private EntityPopulationController entityPopulationController;
    private NewExecutionController newExecutionController;
    private Boolean isICanged;
    private SpinnerValueFactory<Integer> spinnerValueFactory;
    private Boolean counterChangedManually = false;

    @FXML
    public void initialize() {
        count.setEditable(true);
        isICanged = false;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setMaxSize(Integer newMaxSize) {
        currMaxSize = newMaxSize;
        originalMaxSize = newMaxSize;

        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, currMaxSize.intValue(), 0);
        count.setValueFactory(spinnerValueFactory);

        count.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                isICanged = true;

                if(newValue > currMaxSize) {
                    newValue = currMaxSize;
                    counterChangedManually = true;
                    count.getValueFactory().setValue(newValue);
                    entityPopulationController.currentCounterChanged(oldValue, newValue);
                }
                else if (newValue < 0) {
                    newValue = 0;
                    counterChangedManually = true;
                    count.getValueFactory().setValue(newValue);
                    entityPopulationController.currentCounterChanged(oldValue, newValue);
                }
                else if (counterChangedManually == false) {
                    entityPopulationController.currentCounterChanged(oldValue, newValue);
                }
                else {
                    counterChangedManually = false;
                }
            }
        });
    }

    @Override
    public void onChange(int oldCurrValue, int newCurrValue) {
        if (!isICanged) {
            currMaxSize = currMaxSize + (oldCurrValue - newCurrValue);

            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, currMaxSize.intValue(), spinnerValueFactory.getValue());
            count.setValueFactory(spinnerValueFactory);
        }
        isICanged = false;
    }

    @Override
    public void startOnClicked() {
        newExecutionController.addToEntityPopulationDTOList(new EntityPopulationDTO(name.getText(), spinnerValueFactory.getValue()));
    }

    public void setEntityPopulationController(EntityPopulationController entityPopulationController) {
        this.entityPopulationController = entityPopulationController;
    }

    public void setDataMembers(EntityDefinitionDTO entityDefinitionDTO) {
        name.setText(entityDefinitionDTO.getName());
    }
}
