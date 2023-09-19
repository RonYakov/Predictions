package details;

import details.selectedComponent.entity.EntityDetailsController;
import details.selectedComponent.environment.EnvironmentDetailsController;
import details.selectedComponent.grid.GridDetailsController;
import details.selectedComponent.rule.RulesDetailsController;
import details.selectedComponent.termination.TerminationDetailsController;
import details.simulationBreakdown.SimulationBreakdownController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import managerFX.MainScreenController;
import option2.*;

import java.io.IOException;

public class DetailsScreenController {
    @FXML
    private SplitPane simulationBreakdown;
    @FXML
    private SimulationBreakdownController simulationBreakdownController;
    @FXML
    private SplitPane rootDetails;
    private MainScreenController mainScreenController;
    private Double originalDividerPosition;

    @FXML
    public void initialize() {
        simulationBreakdownController.setDetailsScreenController(this);
        originalDividerPosition = 0.3832752613240418; // Store your original value here
        setDivider();
    }


    private void setDivider(){
        rootDetails.setDividerPositions(originalDividerPosition);

        // Add a listener to the divider position
        rootDetails.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            // Reset the position to the original value
            rootDetails.setDividerPositions(originalDividerPosition);
        });
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void entitiesShowButtonClicked(EntityDefinitionDTO EntityData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/entity/EntityDetails.fxml"));
            Parent entityContent = loader.load();
            EntityDetailsController entityDetailsController = loader.getController();
            rootDetails.getItems().set(1, entityContent);

            entityDetailsController.setAllDataMembers(EntityData);

        } catch (IOException e) {
        }
    }

    public void environmentShowButtonClicked(PropertyDefinitionDTO EnvironmentData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/environment/EnvironmentDetails.fxml"));
            Parent entityContent = loader.load();
            EnvironmentDetailsController environmentDetailsController = loader.getController();
            rootDetails.getItems().set(1, entityContent);

            environmentDetailsController.setData(EnvironmentData);

        } catch (IOException e) {
        }
    }


    public void rulesShowButtonClicked(RulesDTO rulesDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/rule/RulesDetails.fxml"));
            Parent ruleContent = loader.load();
            RulesDetailsController rulesDetailsController = loader.getController();
            rootDetails.getItems().set(1, ruleContent);

            rulesDetailsController.setAllDataMembers(rulesDTO);

        } catch (IOException e) {
        }
    }

    public void terminationShowButtonClicked(TerminationDTO terminationDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/termination/TerminationDetails.fxml"));
            Parent terminationContent = loader.load();
            TerminationDetailsController terminationDetailsController = loader.getController();
            rootDetails.getItems().set(1, terminationContent);

            terminationDetailsController.setAllDataMembers(terminationDTO);

        } catch (IOException e) {
        }
    }
    public void gridShowButtonClicked(Integer rows, Integer cols) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details/selectedComponent/grid/GridDetails.fxml"));
            Parent GridContent = loader.load();
            GridDetailsController gridDetailsController = loader.getController();
            rootDetails.getItems().set(1, GridContent);

            gridDetailsController.setData(rows.toString(), cols.toString());

        } catch (IOException e) {
        }
    }

    public void initializeDetailsData(SimulationDefinitionDTO simulationDefinitionDTO) {
        simulationBreakdownController.initializeDetailsData(simulationDefinitionDTO);
    }

    public void setOnColorChange(String color) {
        rootDetails.setStyle("-fx-background-color: " + color + ";");
        simulationBreakdown.setStyle("-fx-background-color: " + color + ";");
        simulationBreakdownController.setOnColorChange(color);
    }

}
