package entity.definition;

import property.definition.PropertyDefinition;

import java.io.Serializable;
import java.util.Map;

public class EntityDefinition implements Serializable {
    private final String name;
    private Map<String, PropertyDefinition> properties;
    private Integer population;

    public EntityDefinition(String name ,Map<String, PropertyDefinition> properties) {
        this.name = name;
        this.properties = properties;
        population = null;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public PropertyDefinition getProperty(String propName) {
        return properties.get(propName);
    }

    public Map<String, PropertyDefinition> getProperties() {
        return properties;
    }
}
