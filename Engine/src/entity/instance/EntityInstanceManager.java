package entity.instance;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EntityInstanceManager implements Serializable {
    private final String name;
    private List<EntityInstance> entityInstanceList;

    public EntityInstanceManager(String name, List<EntityInstance> list) {
        this.name = name;
        this.entityInstanceList = list;
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
