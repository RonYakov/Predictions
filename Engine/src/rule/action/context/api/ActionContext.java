package rule.action.context.api;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import property.instance.AbstractPropertyInstance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ActionContext {

    void setRows(int rows);
    void setCols(int cols);
    void setStopAction(boolean stopAction);
    boolean getStopAction();
    String getSecondaryEntityName ();
    void setSecondaryEntityName(String name);

    int getRows();
    int getCols();
    EntityInstance getPrimaryEntityInstance();
    void setSecondaryEntity(EntityInstance secondaryEntity);
    EntityInstance getSecondaryEntityInstance();
    void setEntityManager(EntityInstanceManager entityManager);
    void setPrimaryEntityInstance(EntityInstance entity);
    void setEnvironments(Map<String, AbstractPropertyInstance> environments);
    Map<String, AbstractPropertyInstance> getEnvironments();

}
