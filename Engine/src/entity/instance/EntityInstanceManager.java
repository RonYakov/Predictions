package entity.instance;

import entity.definition.EntityDefinition;
import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityInstanceManager implements Serializable {
    private final String name;
    private List<EntityInstance> entityInstanceList;
    private EntityDefinition entityDefinition;
    private List<Integer> populationHistory;


    public EntityInstanceManager(String name, List<EntityInstance> list,EntityDefinition entityDefinition) {
        this.name = name;
        this.entityInstanceList = list;
        this.entityDefinition = entityDefinition;
        populationHistory = new ArrayList<>();
    }

    public List<EntityInstance> getEntityInstanceList() {
        return entityInstanceList;
    }

    public void killEntity(EntityInstance entityToKill){

        entityInstanceList.remove(entityToKill);
    }
    public String getName() {
        return name;
    }

    public void addPopulationToHistory() {
        populationHistory.add(entityInstanceList.size());
    }

    public Double avgPropertyNotChanged(String propertyName) {
        Integer sum = new Integer(0);
        Integer count = new Integer(0);

        for(EntityInstance entityInstance : entityInstanceList) {
            AbstractPropertyInstance propertyInstance = entityInstance.getProperty(propertyName);
            sum += propertyInstance.getUnchangedSum();
            count += propertyInstance.getChangesCounter();
        }
        if(count == 0) {
            return 0.0;
        }
        else {
            return (double)sum / count;
        }
    }

    public Double avgPropertyValue(String propertyName) {
        Double sum = new Double(0);
        Integer count = new Integer(0);
        if(entityDefinition.getProperty(propertyName).getType() == PropertyType.DECIMAL || entityDefinition.getProperty(propertyName).getType() == PropertyType.FLOAT) {
            for(EntityInstance entityInstance : entityInstanceList) {
                sum += Double.parseDouble(entityInstance.getProperty(propertyName).getValue());
                count++;
            }

            if(count == 0) {
                return 0.0;
            }
            else {
                return sum / count;
            }
        }
        return null;
    }

    public void setEntityInstanceList(List<EntityInstance> entityInstanceList) {
        this.entityInstanceList = entityInstanceList;
    }

    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public Integer getPopulation() {
        return entityInstanceList.size();
    }

    public List<Integer> getPopulationHistory() {
        return populationHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInstanceManager that = (EntityInstanceManager) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
