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
    private Integer gridSize;

    private EntityPopulationController entityPopulationController;

    @FXML
    public void initialize() {
        count.setEditable(true);

        SpinnerValueFactory<Integer> range = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, gridSize.intValue(), 0);
        count.setValueFactory(range);
    }

    @Override
    public void onChange(int oldCurrValue, int newCurrValue) {
        //todo mahar
    }

    public void setEntityPopulationController(EntityPopulationController entityPopulationController) {
        this.entityPopulationController = entityPopulationController;
    }

    public void setDataMembers(EntityDefinitionDTO entityDefinitionDTO) {
        name.setText(entityDefinitionDTO.getName());
    }

    private void spinnerDataChanged() {
        count.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                entityPopulationController.currentCounterChanged(oldValue, newValue);
            }
        });
    }
}
