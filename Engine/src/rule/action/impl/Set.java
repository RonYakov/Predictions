package rule.action.impl;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import exception.SecondEntityIgnoreException;
import exception.TypeUnmatchedException;
import expression.ExpressionType;
import expression.api.Expression;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.SetDTO;
import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.secondaryEntity.SecondaryEntity;

public class Set extends AbstractAction {
    private final String property;
    private final Expression value;

    public Set(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition, String property, Expression value) {
        super(primaryEntityDefinition, secondaryEntityDefinition,ActionType.SET);
        this.property = property;
        this.value = value;
    }
    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntity() == null) {
            return new SetDTO("Set", getPrimaryEntityDefinition().getName(), null,
                    value.GetSimpleValue(), property);
        }
        return new SetDTO("Set", getPrimaryEntityDefinition().getName(), getSecondaryEntity().getEntityName(),
                value.GetSimpleValue(), property);
    }
    private EntityInstance getEntityForInvoke(ActionContext context) {
        if(context.getPrimaryEntityInstance().getEntType().equals(getPrimaryEntityDefinition().getName())) {
            return context.getPrimaryEntityInstance();
        }
        else if(context.getSecondaryEntityName().equals(getPrimaryEntityDefinition().getName())) {
            return context.getSecondaryEntityInstance();
        }
        else {
            throw new RuntimeException("Entity exception! The entity: " + getPrimaryEntityDefinition().getName() + " in is not valid.\n" +
                    "In this function the main entity must be: " + context.getPrimaryEntityInstance().getEntType() + (context.getSecondaryEntityInstance() == null ? "." : (" or: " + context.getSecondaryEntityInstance().getEntType())));
        }
    }
    private EntityInstance getOtherEntity(EntityInstance entityInstance, ActionContext context) {
        if(context.getPrimaryEntityInstance().equals(entityInstance)) {
            return context.getSecondaryEntityInstance();
        }
        else {
            return context.getPrimaryEntityInstance();
        }
    }

    @Override
    public void Invoke(ActionContext context) {
        EntityInstance entityInstance = getEntityForInvoke(context);
        if(entityInstance == null){
            return;
        }

        EntityInstance otherEntity = getOtherEntity(entityInstance, context);
        Boolean isSeconderyShouldExist = true;
        if(otherEntity == null) {
            if(context.getSecondaryEntityName() == null){
                isSeconderyShouldExist = false;
            }
        }
        PropertyType propertyType = entityInstance.getProperty(property).getType();
        AbstractPropertyInstance propertyToSet = entityInstance.getProperty(property);
        ExpressionType valueType = value.getType();

        try {
            String newValue = value.GetExplicitValue(entityInstance, otherEntity, context.getEnvironments(), isSeconderyShouldExist);

            if(propertyType == PropertyType.DECIMAL && valueType == ExpressionType.INT) {
                Integer numberNewValue = Integer.parseInt(newValue);
                propertyToSet.setValue(checkIfActionResultIsInRange(numberNewValue, propertyToSet).toString());
                return;
            }
            if(propertyType == PropertyType.FLOAT && (valueType == ExpressionType.INT || valueType == ExpressionType.FLOAT)) {
                Float numberNewValue = Float.parseFloat(newValue);
                propertyToSet.setValue(checkIfActionResultIsInRange(numberNewValue, propertyToSet).toString());
                return;
            }
            if(propertyType == PropertyType.BOOLEAN && valueType == ExpressionType.BOOLEAN) {
                context.getPrimaryEntityInstance().getProperty(property).setValue(value.GetExplicitValue(entityInstance, otherEntity, context.getEnvironments(), isSeconderyShouldExist));
                return;
            }
            if(propertyType == PropertyType.STRING && valueType == ExpressionType.STRING) {
                context.getPrimaryEntityInstance().getProperty(property).setValue(value.GetExplicitValue(entityInstance, otherEntity, context.getEnvironments(), isSeconderyShouldExist));
                return;
            }
        } catch(SecondEntityIgnoreException ignore) {
        }
        if(propertyType == PropertyType.DECIMAL && valueType == ExpressionType.FLOAT) {
            throw new TypeUnmatchedException("TypeUnmatchedException: can not set the property '" + property + "' with expression: " + value.GetSimpleValue() + ".\n" +
                    "Note that you can not set a decimal property with float expression. Problem occurred in class Set when trying to do set action");
        }

        throw new TypeUnmatchedException("TypeUnmatchedException: can not set the property '" + property + "' with expression: " + value.GetSimpleValue() + ".\n" +
                "Note that you need to match the property type and expression type to be able to set them. Problem occurred in class Set when trying to do set action");

    }
}
