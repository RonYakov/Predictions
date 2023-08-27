package entity.instance;

import property.definition.PropertyDefinition;
import property.instance.AbstractPropertyInstance;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class EntityInstance implements Serializable {
    private final Map<String, AbstractPropertyInstance> properties;

    private Boolean toKill;

    public EntityInstance(Map<String, AbstractPropertyInstance> properties) {
        this.properties = properties;
        toKill = false;
    }


    public AbstractPropertyInstance getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public String getSpecificPropertyValue (String propertyName) {
        String res;
        AbstractPropertyInstance propertyInstance = properties.get(propertyName);

        if(propertyInstance != null) {
            res = propertyInstance.getValue();
        }
        else {
            res = null;
        }

        return  res;
    }

    public void killMe() {
        toKill = true;
    }

    public Boolean getToKill() {
        return toKill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInstance that = (EntityInstance) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
