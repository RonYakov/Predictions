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
    private int rows;
    private int cols;
    private boolean stopAction;
    private String secondaryEntityName;
    private Map<String, AbstractPropertyInstance> environments;

    public ActionContextImpl() {
        primaryEntity = null;
        secondaryEntity = null;
        entityManager = null;
        stopAction = false;
    }

    @Override
    public void setEnvironments(Map<String, AbstractPropertyInstance> environments) {
        this.environments = environments;
    }
    @Override
    public Map<String, AbstractPropertyInstance> getEnvironments() {
        return this.environments;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }
    @Override
    public void setCols(int cols) {
        this.cols = cols;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public boolean getStopAction() {
        return stopAction;
    }

    @Override
    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

    @Override
    public void setSecondaryEntityName(String name) {
        secondaryEntityName = name;
    }

    @Override
    public void setStopAction(boolean stopAction) {
        this.stopAction = stopAction;
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
}
