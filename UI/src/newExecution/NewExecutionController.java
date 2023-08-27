package newExecution;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import managerFX.MainScreenController;

public class NewExecutionController {

    @FXML
    private Button clearButton;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private Button startButton;
    private MainScreenController mainScreenController;
    private Double originalDividerPosition;

    @FXML
    public void initialize() {
        originalDividerPosition = 0.41; // Store your original value here
        setDivider();
    }

    private void setDivider(){
        mainSplitPane.setDividerPositions(originalDividerPosition);

        // Add a listener to the divider position
        mainSplitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            // Reset the position to the original value
            mainSplitPane.setDividerPositions(originalDividerPosition);
        });
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    void clearButtonClicked(ActionEvent event) {

    }

    @FXML
    void startButtonClicked(ActionEvent event) {

    }

}
