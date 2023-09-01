package newExecution.entitiesPopulation.entityCount;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.entitiesPopulation.EntityPopulationController;
import option2.EntityDefinitionDTO;

public class EntityCountController implements PopulationCountListener {

    @FXML
    private Spinner<Integer> count;

    @FXML
    private Label name;
    private Integer maxSize;

    private EntityPopulationController entityPopulationController;

    private Boolean isICanged;

    private SpinnerValueFactory<Integer> spinnerValueFactory;

    @FXML
    public void initialize() {
        count.setEditable(true);
        isICanged = false;
    }

    public void setMaxSize(Integer newMaxSize) {
        maxSize = newMaxSize;

        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxSize.intValue(), 0);
        count.setValueFactory(spinnerValueFactory);

        count.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                isICanged = true;
                entityPopulationController.currentCounterChanged(oldValue, newValue);
            }
        });
    }

    @Override
    public void onChange(int oldCurrValue, int newCurrValue) {
        if (!isICanged) {
            maxSize = maxSize + (oldCurrValue - newCurrValue);

            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxSize.intValue(), spinnerValueFactory.getValue());
            count.setValueFactory(spinnerValueFactory);
        }
        isICanged = false;
    }

    public void setEntityPopulationController(EntityPopulationController entityPopulationController) {
        this.entityPopulationController = entityPopulationController;
    }

    public void setDataMembers(EntityDefinitionDTO entityDefinitionDTO) {
        name.setText(entityDefinitionDTO.getName());
    }

}
