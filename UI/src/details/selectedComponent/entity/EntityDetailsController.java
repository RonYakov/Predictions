package details.selectedComponent.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import option2.EntityDefinitionDTO;
import option2.PropertyDefinitionDTO;

public class EntityDetailsController {

    @FXML
    private Label entityName;

    @FXML
    private TableColumn<PropertyDefinitionDTO, Double> fromCol;

    @FXML
    private TableColumn<PropertyDefinitionDTO, String> nameCol;

    @FXML
    private TableView<PropertyDefinitionDTO> propertiesTableView;

    @FXML
    private TableColumn<PropertyDefinitionDTO, Boolean> randomCol;

    @FXML
    private TableColumn<PropertyDefinitionDTO, Double> toCol;

    @FXML
    private TableColumn<PropertyDefinitionDTO, String> typeCol;

    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        typeCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getType());
            return property;
        });
        randomCol.setCellValueFactory(cellData -> {
            SimpleBooleanProperty property = new SimpleBooleanProperty();
            property.setValue(cellData.getValue().getRandomize());
            return property;
        });
        toCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getRangeTo());
            return property.asObject();
        });
        fromCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getRangeFrom());
            return property.asObject();
        });
    }
    public void setAllDataMembers(EntityDefinitionDTO EntityData) {
        entityName.setText(EntityData.getName());

        ObservableList<PropertyDefinitionDTO> data = FXCollections.observableArrayList(EntityData.getProperties());
        propertiesTableView.setItems(data);
    }

}
