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
    private EntityInstance secondaryEntity;
    private EntityInstanceManager entityManager;

    public ActionContextImpl() {
        primaryEntity = null;
        secondaryEntity = null;
        entityManager = null;
    }

    @Override
    public void setSecondaryEntity(EntityInstance secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    @Override
    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntity;
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
