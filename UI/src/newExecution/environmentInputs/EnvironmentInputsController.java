package newExecution.environmentInputs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import newExecution.NewExecutionController;
import newExecution.environmentInputs.typeBoolean.EnvTypeBooleanController;
import newExecution.environmentInputs.typeNumber.EnvTypeNumberController;
import newExecution.environmentInputs.typeString.EnvTypeStringController;
import option3.EnvironmentDefinitionDTO;
import option3.EnvironmentDefinitionListDTO;

import java.io.IOException;

public class EnvironmentInputsController {

    @FXML
    private VBox environmetsVbox;
    private NewExecutionController newExecutionController;
    private EnvironmentDefinitionListDTO environmentData;
    @FXML
    private ScrollPane environmentsInputScrollPane;

    public void setEnvironmentData(EnvironmentDefinitionListDTO environmentDefinitionListDTO) {
        environmentData = environmentDefinitionListDTO;
        fillEnvironmentsVbox();
    }
    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    private void fillEnvironmentsVbox() {
        for (EnvironmentDefinitionDTO currEnv : environmentData.getEnvironmentDefinitionDTOList()) {
            switch (currEnv.getType()) {
                case "DECIMAL":
                    createNumberEnvData(currEnv);
                    break;
                case "FLOAT":
                    createNumberEnvData(currEnv);
                    break;
                case "BOOLEAN":
                    createBooleanEnvData(currEnv);
                    break;
                case "STRING":
                    createStringEnvData(currEnv);
                    break;
            }
        }
    }

    private void createStringEnvData(EnvironmentDefinitionDTO currEnv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/environmentInputs/typeString/EnvTypeString.fxml"));
            Parent envStringContent = loader.load();
            EnvTypeStringController envTypeStringController = loader.getController();
            envTypeStringController.setData(currEnv);

            envTypeStringController.setNewExecutionController(newExecutionController);
            newExecutionController.addRerunEnvironmentListener(envTypeStringController);
            environmetsVbox.getChildren().add(envStringContent);

        } catch (IOException e) {
        }
    }

    private void createBooleanEnvData(EnvironmentDefinitionDTO currEnv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/environmentInputs/typeBoolean/EnvTypeBoolean.fxml"));
            Parent envBooleanContent = loader.load();
            EnvTypeBooleanController envTypeBooleanController = loader.getController();
            envTypeBooleanController.setData(currEnv);

            envTypeBooleanController.setNewExecutionController(newExecutionController);
            newExecutionController.addRerunEnvironmentListener(envTypeBooleanController);
            environmetsVbox.getChildren().add(envBooleanContent);

        } catch (IOException e) {
        }
    }

    private void createNumberEnvData(EnvironmentDefinitionDTO currEnv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/environmentInputs/typeNumber/EnvTypeNumber.fxml"));
            Parent envNumberContent = loader.load();
            EnvTypeNumberController envTypeNumberController = loader.getController();
            envTypeNumberController.setData(currEnv);

            envTypeNumberController.setNewExecutionController(newExecutionController);
            newExecutionController.addRerunEnvironmentListener(envTypeNumberController);
            environmetsVbox.getChildren().add(envNumberContent);

        } catch (IOException e) {
        }
    }
    public void setOnColorChange(String color) {
        environmetsVbox.setStyle("-fx-background-color: " + color + ";");
        environmentsInputScrollPane.setStyle("-fx-background-color: " + color + ";");

    }
}
