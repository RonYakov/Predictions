package entity.definition;

import property.definition.PropertyDefinition;

import java.io.Serializable;
import java.util.Map;

public class EntityDefinition implements Serializable {
    private final String name;
    private Map<String, PropertyDefinition> properties;
    private final int population;

    public EntityDefinition(String name,int population ,Map<String, PropertyDefinition> properties) {
        this.name = name;
        this.properties = properties;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public PropertyDefinition getProperty(String propName) {
        return properties.get(propName);
    }

    public Map<String, PropertyDefinition> getProperties() {
        return properties;
    }
}
