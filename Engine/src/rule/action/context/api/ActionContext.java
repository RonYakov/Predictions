package rule.action.context.api;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import property.instance.AbstractPropertyInstance;

import java.io.Serializable;
import java.util.List;

public interface ActionContext {
    EntityInstance getPrimaryEntityInstance();
    void setSecondaryEntity(EntityInstance secondaryEntity);
    EntityInstance getSecondaryEntityInstance();
    void setEntityManager(EntityInstanceManager entityManager);
    void setPrimaryEntityInstance(EntityInstance entity);
    void removeEntity(EntityInstance entity);
}
