package details.selectedComponent.entity;

import details.selectedComponent.entity.property.PropertyDetailsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import option2.EntityDefinitionDTO;
import option2.PropertyDefinitionDTO;

public class EntityDetailsController {

    @FXML
    private Label entityName;

    @FXML
    private VBox propertiesVbox;

    public void setAllDataMembers(EntityDefinitionDTO EntityData) {
        entityName.setText(EntityData.getName());

        for (PropertyDefinitionDTO propertyDefinitionDTO : EntityData.getProperties()) {
            setNextProperty(propertyDefinitionDTO);
        }
    }

    private void setNextProperty(PropertyDefinitionDTO propertyDefinitionDTO) {
        PropertyDetailsController propertyDetailsController;

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/entity/property/PropertyDetails.fxml"));
            Parent detailsContent = loader.load();
            propertyDetailsController = loader.getController();
            propertyDetailsController.setData(propertyDefinitionDTO);
            propertiesVbox.getChildren().add(detailsContent);
        } catch (Exception ignore) {
        }
    }

}
