package results.simulationResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import manager.PredictionManager;
import option2.EntityDefinitionDTO;
import option2.PropertyDefinitionDTO;
import option4.InfoType;
import option4.SimulationDesiredInfoDTO;
import option4.histogram.HistogramSingleEntityDTO;
import option4.histogram.HistogramSinglePropDTO;
import option4.histogram.HistogramSpecificPropDTO;

import java.util.List;

public class SimulationResultController {
    @FXML
    private ComboBox<String> analyseChoice;
    @FXML
    private ComboBox<String> entityChoice;
    @FXML
    private ComboBox<String> informationChoice;
    @FXML
    private ComboBox<String> propertyChoice;
    @FXML
    private Button rerunButton;
    @FXML
    private Button showButton;
    @FXML
    private Pane showPane;
    private PredictionManager predictionManager;
    private Integer id;

    @FXML
    private void initialize() {
        entityChoice.setVisible(false);
        propertyChoice.setVisible(false);
        informationChoice.setVisible(false);
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private void setEntityCBox() {
        ObservableList<String> entitiesName = FXCollections.observableArrayList();
        for(EntityDefinitionDTO entityDefinitionDTO : predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList()) {
            entitiesName.add(entityDefinitionDTO.getName());
        }
        entityChoice.setItems(entitiesName);
    }
    private void setPropertyCBox(List<PropertyDefinitionDTO> properties) {
        ObservableList<String> propertiesName = FXCollections.observableArrayList();
        for(PropertyDefinitionDTO propertyDefinitionDTO : properties) {
            propertiesName.add(propertyDefinitionDTO.getName());
        }
        propertyChoice.setItems(propertiesName);
    }
    private void setInformationCBox(PropertyDefinitionDTO propertyDefinitionDTO) {
        ObservableList<String> choices = FXCollections.observableArrayList("Population histogram", "Consistency - Average steps the value did not change");
        if (propertyDefinitionDTO != null && propertyDefinitionDTO.getType() != null) {
            if (propertyDefinitionDTO.getType().equals("DECIMAL") || propertyDefinitionDTO.getType().equals("FLOAT")) {
                choices.add("Average value of the property");
            }
        }

        informationChoice.setItems(choices);
    }
    @FXML
    void analyseClicked(ActionEvent event) {
        showPane.getChildren().clear();
        entityChoice.setValue(null);
        if(analyseChoice.getValue() != null){
            if(analyseChoice.getValue().equals("Entity's quantity graph")){
                propertyChoice.setVisible(false);
                informationChoice.setVisible(false);
            }
            setEntityCBox();
            entityChoice.setVisible(true);
        }
    }
    @FXML
    void entityClicked(ActionEvent event) {
        showPane.getChildren().clear();
        propertyChoice.setValue(null);
        informationChoice.setValue(null);
        if(entityChoice.getValue() != null){
            if(analyseChoice.getValue().equals("Entity's quantity graph")){
                loadEntityQuantityController();
            }
            else if (analyseChoice.getValue().equals("Property's statistical info")) {
                setPropertyCBox(findEntityDefinitionDTO(entityChoice.getValue(), predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList()).getProperties());
                propertyChoice.setVisible(true);
            }
        }
    }

    private void loadEntityQuantityController() {
    }

    private EntityDefinitionDTO findEntityDefinitionDTO(String name, List<EntityDefinitionDTO> entityDefinitionDTOList) {
        EntityDefinitionDTO res = null;

        for(EntityDefinitionDTO entityDefinitionDTO : entityDefinitionDTOList) {
            if(entityDefinitionDTO.getName().equals(name)) {
                res = entityDefinitionDTO;
            }
        }
        return res;
    }

    @FXML
    void informationClicked(ActionEvent event) {
        showPane.getChildren().clear();
        if(informationChoice.getValue() != null) {
            if (informationChoice.getValue().equals("Histogram on the population")) {
                loadHistogramController(predictionManager.getHistogram(new HistogramSinglePropDTO(propertyChoice.getValue()), new SimulationDesiredInfoDTO(id, InfoType.HISTOGRAM), new HistogramSingleEntityDTO(entityChoice.getValue())));
            }
//            else if (informationChoice.getValue().equals("Consistency - Average steps the value did not change")) {
//                loadConsistencyController();
//                consistencyController.setter("Consistency - Average steps the property " + propertyCBox.getValue() + " did not change:",
//                        simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()).getConsistency());
//            }
//            else if (informationChoice.getValue().equals("Average value of the property in the population")) {
//                loadConsistencyController();
//                consistencyController.setter("Average value of the property " + propertyCBox.getValue() + " in the population:",
//                        simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()).getAverageOfPropertyInPopulation());
//            }
        }
    }

    private void loadHistogramController(HistogramSpecificPropDTO histogram) {
    }

    @FXML
    void propertyClicked(ActionEvent event) {
        showPane.getChildren().clear();
        informationChoice.setValue(null);
        if(propertyChoice.getValue() != null){
            setInformationCBox(findPropertyDefinitionDTO(propertyChoice.getValue(), findEntityDefinitionDTO(entityChoice.getValue(), predictionManager.showCurrentSimulationData().getEntityDefinitionDTOList()).getProperties()));
            informationChoice.setVisible(true);
        }
    }

    private PropertyDefinitionDTO findPropertyDefinitionDTO(String name, List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        PropertyDefinitionDTO res = null;

        for(PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOList) {
            if(propertyDefinitionDTO.getName().equals(name)) {
                res = propertyDefinitionDTO;
            }
        }
        return res;
    }
}