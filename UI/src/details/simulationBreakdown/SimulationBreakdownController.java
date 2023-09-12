package details.simulationBreakdown;

import details.DetailsScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import option2.*;

import java.util.List;
import java.util.Optional;

public class SimulationBreakdownController {

    @FXML
    private ChoiceBox<String> choiceBoxEntities;
    @FXML
    private ChoiceBox<String> choiceBoxEnvironment = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> choiceBoxRules;
    @FXML
    private Button showEntites;
    @FXML
    private Button showEnvironment;
    @FXML
    private Button showGrid;
    @FXML
    private Button showRules;
    @FXML
    private Button showTermination;
    @FXML
    private SplitPane root;
    private SimulationDefinitionDTO simulationDefinitionDTO;
    private DetailsScreenController detailsScreenController;
    private double originalDividerPosition;

    @FXML
    public void initialize() {
        originalDividerPosition = 0.3956043956043956; // Store your original value here
        setDivider();
    }

    private void setDivider(){
        root.setDividerPositions(originalDividerPosition);

        // Add a listener to the divider position
        root.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            // Reset the position to the original value
            root.setDividerPositions(originalDividerPosition);
        });
    }
    public void setDetailsScreenController(DetailsScreenController detailsScreenController) {
        this.detailsScreenController = detailsScreenController;
    }

    @FXML
    private void EntitiesButtonClicked(ActionEvent event) {
        String newValue = choiceBoxEntities.getSelectionModel().getSelectedItem();
            if (newValue != null) {
                Optional<EntityDefinitionDTO> entityDefinitionDTO = simulationDefinitionDTO.getEntityDefinitionDTOList().stream()
                        .filter(myObject -> myObject.getName().equals(newValue))
                        .findFirst();
                detailsScreenController.entitiesShowButtonClicked(entityDefinitionDTO.get());
            }
    }
    @FXML
    private void RulesButtonClicked(ActionEvent event) {
        String newValue = choiceBoxRules.getSelectionModel().getSelectedItem();
        if(newValue != null) {
            Optional<RulesDTO> rulesDTO = simulationDefinitionDTO.getRulesDTOList().stream()
                    .filter(myObject -> myObject.getName().equals(newValue))
                    .findFirst();
                    detailsScreenController.rulesShowButtonClicked(rulesDTO.get());
        }

    }
    @FXML
    private void EnvironmentButtonClicked(ActionEvent event) {
        String newValue = choiceBoxEnvironment.getSelectionModel().getSelectedItem();
        if (newValue != null) {
            Optional<PropertyDefinitionDTO> environmentDefinitionDTO = simulationDefinitionDTO.getEnvironmentDefenitionDTOList().stream()
                    .filter(myObject -> myObject.getName().equals(newValue))
                    .findFirst();
            detailsScreenController.environmentShowButtonClicked(environmentDefinitionDTO.get());
        }
    }
    @FXML
    private void TerminationButtonClicked(ActionEvent event) {
        detailsScreenController.terminationShowButtonClicked(simulationDefinitionDTO.getTerminationDTO());
    }


    @FXML
    private void GridButtonClicked(ActionEvent event) {
        detailsScreenController.gridShowButtonClicked(simulationDefinitionDTO.getGridRows(), simulationDefinitionDTO.getGridCols());
    }

    public void initializeDetailsData(SimulationDefinitionDTO simulationDefinitionDTO) {
        this.simulationDefinitionDTO = simulationDefinitionDTO;
        initializeEntities(simulationDefinitionDTO.getEntityDefinitionDTOList());
        initializeRules(simulationDefinitionDTO.getRulesDTOList());
        initializeEnvironments(simulationDefinitionDTO.getEnvironmentDefenitionDTOList());
    }

    private void initializeRules(List<RulesDTO> rulesDTOList) {
        for(RulesDTO rulesDTO : rulesDTOList) {
            choiceBoxRules.getItems().add(rulesDTO.getName());
        }
    }

    private void initializeEntities(List<EntityDefinitionDTO> entityDefinitionDTOList) {
        for(EntityDefinitionDTO entityDefinitionDTO : entityDefinitionDTOList) {
            choiceBoxEntities.getItems().add(entityDefinitionDTO.getName());
        }
    }
    private void initializeEnvironments(List<PropertyDefinitionDTO> environmentDefenitionDTOList) {
        for(PropertyDefinitionDTO environmentDefinitionDTO : environmentDefenitionDTOList) {
            choiceBoxEnvironment.getItems().add(environmentDefinitionDTO.getName());
        }
    }
}
