package header;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.PredictionManager;
import managerFX.MainScreenController;
import option1.XmlFullPathDTO;

import java.io.File;
import java.io.IOException;

public class PredictionHeaderController {

    @FXML
    private Label currentFileLabel;

    @FXML
    private Button detailsButton;

    @FXML
    private Label headLineLable;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button newExecutionButton;

    @FXML
    private Button resultsButton;

    private MainScreenController mainScreenController;

    private Parent root;

    private PredictionManager predictionManager = new PredictionManager();
    private BooleanProperty buttonsDisabled = new SimpleBooleanProperty(true);

    public void initialize() {
        detailsButton.disableProperty().bind(buttonsDisabledProperty());
        newExecutionButton.disableProperty().bind(buttonsDisabledProperty());
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public BooleanProperty buttonsDisabledProperty() {
        return buttonsDisabled;
    }

    public void setButtonsDisabled(boolean buttonsDisabled) {
        this.buttonsDisabled.set(buttonsDisabled);
    }

    @FXML
    void loadFileButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        XmlFullPathDTO xmlFullPathDTO = new XmlFullPathDTO(selectedFile.getAbsolutePath());
        try {
            predictionManager.loadXmlData(xmlFullPathDTO);
            currentFileLabel.setText(selectedFile.getAbsolutePath());
            setButtonsDisabled(false);
            mainScreenController.setDetailsSet(false);
        }catch (Exception ignore) {
        }
    }

    @FXML
    void detailsButtonClicked(ActionEvent event) {
        mainScreenController.loadDetailsScreen();
    }

    @FXML
    void newExecutionButtonClicked(ActionEvent event) {
        mainScreenController.newExecutionScreen();
    }

}
