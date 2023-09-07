package factory.instance;

import entity.instance.EntityInstanceManager;
import grid.Grid;
import property.definition.PropertyDefinition;
import property.instance.AbstractPropertyInstance;
import rule.Rule;
import simulation.definition.SimulationDefinition;
import simulation.impl.SimulationRunner;
import simulation.impl.SimulationExecutionDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static factory.instance.FactoryEntityManager.createEntityInstanceManagerMap;
import static factory.instance.FactoryPropertyInstance.createPropertyInstance;


public abstract class FactoryInstance {

    public static SimulationExecutionDetails createSimulation(SimulationDefinition simulationDefinition, int identifyNumber) {
        Map<String, EntityInstanceManager> entityInstanceMap = createEntityInstanceManagerMap(simulationDefinition.getEntitiesDef());
        Map<String, AbstractPropertyInstance> environmentsMap = createPropertyInstanceManagerMap(simulationDefinition.getEnvironmentsDef());

        List<Rule> newRules = new ArrayList<>();
        for(Rule rule : simulationDefinition.getRules()){
               newRules.add(new Rule(rule));
        }

        Grid grid = new Grid(simulationDefinition.getGrid().getRows(), simulationDefinition.getGrid().getCols());
        return new SimulationExecutionDetails(entityInstanceMap, environmentsMap ,grid , newRules, simulationDefinition.getTermination(), identifyNumber);
    }

    private static  Map<String, AbstractPropertyInstance> createPropertyInstanceManagerMap(Map<String, PropertyDefinition> propertyDefinitionMap) {
        Map<String, AbstractPropertyInstance> res = new HashMap<>();
        for(Map.Entry<String, PropertyDefinition> entry : propertyDefinitionMap.entrySet()) {
            res.put(entry.getKey(), createPropertyInstance(entry.getValue()));
        }

        return res;
    }

}
