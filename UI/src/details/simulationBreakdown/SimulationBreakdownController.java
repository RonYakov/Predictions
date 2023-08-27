package details.simulationBreakdown;

import details.DetailsScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;

public class SimulationBreakdownController {

    @FXML
    private ChoiceBox<?> choiceBoxEntities;

    @FXML
    private ChoiceBox<?> choiceBoxEnvironment;

    @FXML
    private ChoiceBox<?> choiceBoxRules;

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
    void EntitiesButtonClicked(ActionEvent event) {
        detailsScreenController.entitiesShowButtonClicked();
    }
    @FXML
    void RulesButtonClicked(ActionEvent event) {
        detailsScreenController.rulesShowButtonClicked();
    }
    @FXML
    void TerminationButtonClicked(ActionEvent event) {
        detailsScreenController.terminationShowButtonClicked();
    }

}
