package factory.instance;

import entity.instance.EntityInstanceManager;
import grid.Grid;
import property.definition.PropertyDefinition;
import property.instance.AbstractPropertyInstance;
import simulation.definition.SimulationDefinition;
import simulation.impl.Simulation;

import java.util.HashMap;
import java.util.Map;

import static factory.instance.FactoryEntityManager.createEntityInstanceManagerMap;
import static factory.instance.FactoryPropertyInstance.createPropertyInstance;


public abstract class FactoryInstance {

    public static Simulation createSimulation(SimulationDefinition simulationDefinition, int identifyNumber) {
        Map<String, EntityInstanceManager> entityInstanceMap = createEntityInstanceManagerMap(simulationDefinition.getEntitiesDef());
        Map<String, AbstractPropertyInstance> environmentsMap = createPropertyInstanceManagerMap(simulationDefinition.getEnvironmentsDef());

        Grid grid = new Grid(simulationDefinition.getGrid().getRows(), simulationDefinition.getGrid().getCols());
        return new Simulation(entityInstanceMap, environmentsMap,grid , simulationDefinition.getRules(), simulationDefinition.getTermination(), identifyNumber);
    }

    private static  Map<String, AbstractPropertyInstance> createPropertyInstanceManagerMap(Map<String, PropertyDefinition> propertyDefinitionMap) {
        Map<String, AbstractPropertyInstance> res = new HashMap<>();
        for(Map.Entry<String, PropertyDefinition> entry : propertyDefinitionMap.entrySet()) {
            res.put(entry.getKey(), createPropertyInstance(entry.getValue()));
        }

        return res;
    }

}
