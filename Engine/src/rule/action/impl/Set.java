package rule.action.impl;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
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
        else if(context.getSecondaryEntityInstance() == null) {
            throw new RuntimeException(); //todo think later
        } else if (context.getSecondaryEntityInstance().getEntType().equals(getPrimaryEntityDefinition().getName())) {
            return context.getSecondaryEntityInstance();
        }
        else {
            throw new RuntimeException(); //todo think later
        }
    }

    @Override
    public void Invoke(ActionContext context) {
        EntityInstance entityInstance = getEntityForInvoke(context);
        PropertyType propertyType = entityInstance.getProperty(property).getType();
        AbstractPropertyInstance propertyToSet = entityInstance.getProperty(property);
        ExpressionType valueType = value.getType();
        String newValue = value.GetExplicitValue(entityInstance);

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
            context.getPrimaryEntityInstance().getProperty(property).setValue(value.GetExplicitValue(context.getPrimaryEntityInstance()));
            return;
        }
        if(propertyType == PropertyType.STRING && valueType == ExpressionType.STRING) {
            context.getPrimaryEntityInstance().getProperty(property).setValue(value.GetExplicitValue(context.getPrimaryEntityInstance()));
            return;
        }
        if(propertyType == PropertyType.DECIMAL && valueType == ExpressionType.FLOAT) {
            throw new TypeUnmatchedException("TypeUnmatchedException: can not set the property '" + property + "' with expression: " + value.GetSimpleValue() + ".\n" +
                    "       Note that you can not set a decimal property with float expression. Problem occurred in class Set when trying to do set action");
        }

        throw new TypeUnmatchedException("TypeUnmatchedException: can not set the property '" + property + "' with expression: " + value.GetSimpleValue() + ".\n" +
                "       Note that you need to match the property type and expression type to be able to set them. Problem occurred in class Set when trying to do set action");

    }
}
