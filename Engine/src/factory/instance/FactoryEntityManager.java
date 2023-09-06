package factory.instance;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import property.definition.PropertyDefinition;
import property.instance.AbstractPropertyInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static factory.instance.FactoryPropertyInstance.createPropertyInstance;

public abstract class FactoryEntityManager {

    public static Map<String, EntityInstanceManager> createEntityInstanceManagerMap(Map<String, EntityDefinition> entityDefinitionMap) {
        Map<String, EntityInstanceManager> res = new HashMap<>();
        for (Map.Entry<String, EntityDefinition> entry : entityDefinitionMap.entrySet()) {
            res.put(entry.getKey(), createEntityInstanceManager(entry.getValue()));
        }

        return res;
    }
    private static EntityInstanceManager createEntityInstanceManager(EntityDefinition value) {
        List<EntityInstance> res = new ArrayList<>();

        for(int i = 0; i < value.getPopulation(); i++) {
            res.add(createEntityInstance(value));
        }

        return new EntityInstanceManager(value.getName(), res , value);
    }
    public static EntityInstance createEntityInstance(EntityDefinition value) {
        Map<String, AbstractPropertyInstance> res = new HashMap<>();

        for(Map.Entry<String, PropertyDefinition> entry : value.getProperties().entrySet()) {
            res.put(entry.getKey(), createPropertyInstance(entry.getValue()));
        }

        return new EntityInstance(res , value.getName());
    }
}
