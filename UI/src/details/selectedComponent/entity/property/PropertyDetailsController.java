package details.selectedComponent.entity.property;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.PropertyDefinitionDTO;

public class PropertyDetailsController {

    @FXML
    private Label isRandomizeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label rangeLabel;

    @FXML
    private Label typeLabel;

    public void setData(PropertyDefinitionDTO propertyDefinitionDTO) {
        nameLabel.setText(propertyDefinitionDTO.getName());
        typeLabel.setText(propertyDefinitionDTO.getType());
        isRandomizeLabel.setText(propertyDefinitionDTO.getRandomize().toString());
        if(propertyDefinitionDTO.getRangeFrom() != null) {
            rangeLabel.setText(propertyDefinitionDTO.getRangeFrom() + " - " + propertyDefinitionDTO.getRangeTo());
        }
        else {
            rangeLabel.setText(" --- ");
        }
    }

}