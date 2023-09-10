package results.simulationDetails.entityDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EntityDetailsController {

    @FXML
    private Label entityCount;

    @FXML
    private Label entityName;

    public void setEntityCount(String entityCount) {
        this.entityCount.setText(entityCount);
    }

    public void setEntityName(String entityName) {
        this.entityName.setText(entityName);
    }
}