package details.selectedComponent.environment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import option2.PropertyDefinitionDTO;

public class EnvironmentDetailsController {

    @FXML
    private Label from;
    @FXML
    private Label name;
    @FXML
    private Label to;
    @FXML
    private Label type;

    public void setData(PropertyDefinitionDTO environmentDefinitionDTO) {
        name.setText(environmentDefinitionDTO.getName());
        type.setText(environmentDefinitionDTO.getType());

        if (environmentDefinitionDTO.getRangeFrom() != null) {
            from.setText(environmentDefinitionDTO.getRangeFrom().toString());
            to.setText(environmentDefinitionDTO.getRangeTo().toString());
        }
        else {
            from.setText("---");
            to.setText("---");
        }
    }
}
