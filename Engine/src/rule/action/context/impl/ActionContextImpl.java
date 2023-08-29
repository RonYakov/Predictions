package rule.action.context.impl;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import property.instance.AbstractPropertyInstance;
import rule.action.context.api.ActionContext;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ActionContextImpl implements ActionContext, Serializable {
    private EntityInstance primaryEntity;
    private List<EntityInstance> secondaryEntities;
    private EntityInstanceManager entityManager;

    public ActionContextImpl() {
        primaryEntity = null;
        secondaryEntities = null;
        entityManager = null;
    }

    @Override
    public void setSecondaryEntities(List<EntityInstance> secondaryEntities) {
        this.secondaryEntities = secondaryEntities;
    }

    @Override
    public void setEntityManager(EntityInstanceManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntity;
    }

    @Override
    public void setPrimaryEntityInstance(EntityInstance entity) {
        this.primaryEntity = entity;
    }

    @Override
    public void removeEntity(EntityInstance entity) {
        entityManager.killEntity(entity);
    }

}
