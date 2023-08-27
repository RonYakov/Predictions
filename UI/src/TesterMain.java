import manager.PredictionManager;
import manager.UIManager;
import option1.XmlFullPathDTO;
import simulation.impl.Simulation;

import static factory.instance.FactoryInstance.createSimulation;

public class TesterMain {
    public static void main(String[] args) {

        UIManager uiManager = new UIManager();
        uiManager.predictionMenu();
    }
}
