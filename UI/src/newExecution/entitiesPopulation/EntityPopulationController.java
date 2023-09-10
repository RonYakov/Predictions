package newExecution.entitiesPopulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import newExecution.NewExecutionController;
import newExecution.entitiesPopulation.entityCount.EntityCountController;
import newExecution.entitiesPopulation.entityCount.PopulationCountListener;
import newExecution.listener.ClearButtonClickedListener;
import option2.EntityDefinitionDTO;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EntityPopulationController {

    @FXML
    private VBox entitiesPopulation;

    @FXML
    private Label currentCountLable;

    @FXML
    private Label maxCountLable;

    private NewExecutionController newExecutionController;
    private List<PopulationCountListener> populationCountListeners = new LinkedList<>();

    @FXML
    public void initialize(){
        currentCountLable.setText("0");
        populationCountListeners.clear();
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setMaxCountLable(int maxSize) {
        maxCountLable.setText(Integer.toString(maxSize));
    }

    public void setEntities(List<EntityDefinitionDTO> entityDefinitionDTOList) {
        entitiesPopulation.getChildren().clear();
        for(EntityDefinitionDTO entityDefinitionDTO : entityDefinitionDTOList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/entitiesPopulation/entityCount/EntityCount.fxml"));
                Parent entityCountLoaderContent = loader.load();
                EntityCountController entityCountController = loader.getController();

                entityCountController.setMaxSize(Integer.parseInt(maxCountLable.getText()));
                entitiesPopulation.getChildren().add(entityCountLoaderContent);
                entityCountController.setNewExecutionController(newExecutionController);
                entityCountController.setDataMembers(entityDefinitionDTO);
                entityCountController.setEntityPopulationController(this);
                populationCountListeners.add(entityCountController);
                newExecutionController.addListenerToStartButton(entityCountController);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void currentCounterChanged(int oldValue, int newValue){
        Integer oldCurrValue = Integer.parseInt(currentCountLable.getText());
        Integer temp = oldCurrValue;
        temp = temp - oldValue + newValue;
        currentCountLable.setText(temp.toString());

        for(PopulationCountListener populationCountListener : populationCountListeners) {
            populationCountListener.onChange(oldCurrValue, temp);
        }
    }


}