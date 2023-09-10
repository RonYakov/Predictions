package entity.instance;

import entity.definition.EntityDefinition;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EntityInstanceManager implements Serializable {
    private final String name;
    private List<EntityInstance> entityInstanceList;
    private EntityDefinition entityDefinition;


    public EntityInstanceManager(String name, List<EntityInstance> list,EntityDefinition entityDefinition) {
        this.name = name;
        this.entityInstanceList = list;
        this.entityDefinition = entityDefinition;
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

    public void setEntityInstanceList(List<EntityInstance> entityInstanceList) {
        this.entityInstanceList = entityInstanceList;
    }

    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public Integer getPopulation() {
        return entityInstanceList.size();
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
