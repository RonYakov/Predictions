package rule.action.impl;

import entity.definition.EntityDefinition;
import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.api.Action;

import java.io.Serializable;

public abstract class AbstractAction implements Action, Serializable {
    private EntityDefinition primaryEntityDefinition;
    private EntityDefinition secondaryEntityDefinition;
    private ActionType type;

    public AbstractAction(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition,ActionType type) {
        this.primaryEntityDefinition = primaryEntityDefinition;
        this.secondaryEntityDefinition = secondaryEntityDefinition;
        this.type = type;
    }

    @Override
    public EntityDefinition getPrimaryEntityDefinition() {
        return primaryEntityDefinition;
    }

    public EntityDefinition getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    protected Number checkIfActionResultIsInRange(Number result , AbstractPropertyInstance propertyInstance){
        if(!(propertyInstance.isInRange(result))){
            if(propertyInstance.getRange().getFrom() > result.doubleValue()){
                result = propertyInstance.getRange().getFrom();
            } else{
                result = propertyInstance.getRange().getTo();
            }
        }
        if(propertyInstance.getType() == PropertyType.DECIMAL) {
            return result.intValue();
        }
        else {
            return result;
        }
    }
}
