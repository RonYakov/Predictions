package entity.instance;

import grid.GridIndex;
import property.instance.AbstractPropertyInstance;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static factory.instance.FactoryEntityManager.createEntityInstance;

public class EntityInstance implements Serializable {
    private final Map<String, AbstractPropertyInstance> properties;
    private final String entType;
    private EntityState state;
    private String replaceMode;
    private GridIndex gridIndex;
    private String whoToReplace = null;



    public EntityInstance(Map<String, AbstractPropertyInstance> properties, String type) {
        this.properties = properties;
        entType = type;
        state = EntityState.LIVING;
        replaceMode = null;
    }

    public String getEntType() {
        return entType;
    }

    public void setGridIndex(GridIndex gridIndex) {
        this.gridIndex = gridIndex;
    }

    public GridIndex getGridIndex() {
        return gridIndex;
    }

    public String getReplaceMode() {
        return replaceMode;
    }

    public void setReplaceMode(String replaceMode) {
        this.replaceMode = replaceMode;
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
        state = EntityState.KILL;
    }

    public EntityState getState() {
        return state;
    }

    public void replaceMe() {state = EntityState.REPLACE;}

    public Map<String, AbstractPropertyInstance> getProperties() {
        return properties;
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

    public EntityInstance replaceMe(EntityInstanceManager entityInstanceManager) {
        EntityInstance replacement = createEntityInstance(entityInstanceManager.getEntityDefinition());

        replacement.setGridIndex(this.gridIndex);
        if(replaceMode.equals("derived")){
            createReplacementProperties(replacement.properties ,this.replaceMode);
        }

        return replacement;
    }

    private void createReplacementProperties(Map<String, AbstractPropertyInstance> target, String replaceMode) {
        List<AbstractPropertyInstance> toReplace = new LinkedList<>( properties.values());

        for (AbstractPropertyInstance property: toReplace) {
            AbstractPropertyInstance change = target.get(property.getName());
            if(change != null && change.getType() == property.getType()){
                change.setValue(property.getValue());
            }
        }
    }

    public void setWhoToReplace(String entityName) {
        whoToReplace = entityName;
    }

    public String getWhoToReplace() {
        return whoToReplace;
    }
}
