package details.selectedComponent.entity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import option2.EntityDefinitionDTO;

public class EntityDetailsController {

    @FXML
    private Label entityName;

    @FXML
    private VBox propertiesVbox;

    public void setAllDataMembers(EntityDefinitionDTO EntityData) {
        entityName.setText(EntityData.getName());
        //todo the propertyBox
    }

}
