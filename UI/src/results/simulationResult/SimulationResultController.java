package results.simulationResult;

import ex2DTO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import manager.PredictionManager;
import managerFX.MainScreenController;
import option2.EntityDefinitionDTO;
import option2.PropertyDefinitionDTO;
import option4.InfoType;
import option4.PastSimulationInfoDTO;
import option4.SimulationDesiredInfoDTO;
import option4.histogram.HistogramSingleEntityDTO;
import option4.histogram.HistogramSinglePropDTO;
import option4.histogram.HistogramSpecificPropDTO;
import results.simulationResult.EntityCounter.EntityCounterController;
import results.simulationResult.consistency.ConsistencyController;
import results.simulationResult.failedCause.FailedCauseController;
import results.simulationResult.histogram.HistogramController;
import results.simulationResult.propAvg.PropAvgController;

import java.io.IOException;
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
    private Label simulationDetails1;
    @FXML
    private Label simulationDetails2;
    @FXML
    private Pane showPane;
    private PredictionManager predictionManager;
    private Integer id;
    private MainScreenController mainScreenController;

    @FXML
    private void initialize() {
        entityChoice.setVisible(false);
        propertyChoice.setVisible(false);
        informationChoice.setVisible(false);
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
        simulationDetails1.setText("Simulation ID: " + id);
        PastSimulationInfoDTO pastSimulationInfoDTO = predictionManager.getPastSimulationInfo(new SimulationIDDTO(id));
        simulationDetails2.setText("Start time:    " + pastSimulationInfoDTO.getDate());

        if(predictionManager.getSimulationState(new SimulationIDDTO(id)).getState().equals("FAILED")){
            loadFailScreen();
        }
    }

    private void loadFailScreen() {
        analyseChoice.setVisible(false);
        rerunButton.setDisable(true);
        rerunButton.setVisible(false);
        FailedCauseDTO failedCause = predictionManager.getFailedCauseDTO(new SimulationIDDTO(id));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/failedCause/FailedCause.fxml"));
            Parent failedCauseData = loader.load();
            FailedCauseController failedCauseController = loader.getController();

            failedCauseController.setSimulationStopCause(failedCause.getFailedCaues());
            showPane.getChildren().add(failedCauseData);
        } catch (IOException e) {
        }
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
    @FXML
    void informationClicked(ActionEvent event) {
        showPane.getChildren().clear();
        if(informationChoice.getValue() != null) {
            if (informationChoice.getValue().equals("Population histogram")) {
                loadHistogramController(predictionManager.getHistogram(new HistogramSinglePropDTO(propertyChoice.getValue()), new SimulationDesiredInfoDTO(id, InfoType.HISTOGRAM), new HistogramSingleEntityDTO(entityChoice.getValue())));
            }
            else if (informationChoice.getValue().equals("Consistency - Average steps the value did not change")) {
                loadConsistencyController();
            }
            else if (informationChoice.getValue().equals("Average value of the property")) {
                loadPropAvgController();
            }
        }
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


    private void loadEntityQuantityController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/EntityCounter/EntityCounter.fxml"));
            Parent EntityCounter = loader.load();
            EntityCounterController entityCounterController = loader.getController();

            EntityCountResDTO entityCountResDTO = predictionManager.getEntityCounters(new EntityCountReqDTO(entityChoice.getValue(), id));
                entityCounterController.addToChart(entityCountResDTO.getEntityCountSamples());

            showPane.getChildren().add(EntityCounter);
        } catch (IOException e) {
        }
    }
    private void loadConsistencyController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/consistency/Consistency.fxml"));
            Parent consistencyData = loader.load();
            ConsistencyController consistencyController = loader.getController();
            ConsistencyReqDTO consistencyReqDTO = new ConsistencyReqDTO(entityChoice.getValue(), propertyChoice.getValue(), id);
            String consistencyAvg = predictionManager.getConsistency(consistencyReqDTO).getAvg().toString();
            consistencyController.setConsistencyAverage(consistencyAvg);
            showPane.getChildren().add(consistencyData);
        } catch (IOException e) {
        }
    }
    private void loadPropAvgController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/propAvg/PropAvg.fxml"));
            Parent propAvgData = loader.load();
            PropAvgController propAvgController = loader.getController();
            ConsistencyReqDTO consistencyReqDTO = new ConsistencyReqDTO(entityChoice.getValue(), propertyChoice.getValue(), id);
            String consistencyAvg = predictionManager.getPropAvg(consistencyReqDTO).getAvg().toString();
            propAvgController.setPropAvg(consistencyAvg);
            showPane.getChildren().add(propAvgData);
        } catch (IOException e) {
        }
    }
    private void loadHistogramController(HistogramSpecificPropDTO histogram) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/simulationResult/histogram/Histogram.fxml"));
            Parent HistogramData = loader.load();
            HistogramController histogramController = loader.getController();

            histogram.getHistogram().forEach((key, value) -> {
                histogramController.addDataToTable(key, value.toString());
            });

            showPane.getChildren().add(HistogramData);
        } catch (IOException e) {
        }
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

    private PropertyDefinitionDTO findPropertyDefinitionDTO(String name, List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        PropertyDefinitionDTO res = null;

        for(PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOList) {
            if(propertyDefinitionDTO.getName().equals(name)) {
                res = propertyDefinitionDTO;
            }
        }
        return res;
    }

    @FXML
    void onRerunClicked(ActionEvent event) {
        mainScreenController.rerunClicked(id);
    }
}