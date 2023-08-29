package rule.action.impl;

import entity.definition.EntityDefinition;
import exception.TypeUnmatchedException;
import expression.ExpressionType;
import expression.api.Expression;
import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;

public class Set extends AbstractAction {
    private final String property;
    private final Expression value;

    public Set(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition,String property, Expression value) {
        super(primaryEntityDefinition, secondaryEntityDefinition,ActionType.SET);
        this.property = property;
        this.value = value;
    }

    @Override
    public void Invoke(ActionContext context) {
        PropertyType propertyType = context.getPrimaryEntityInstance().getProperty(property).getType();
        AbstractPropertyInstance propertyToSet = context.getPrimaryEntityInstance().getProperty(property);
        ExpressionType valueType = value.getType();
        String newValue = value.GetExplicitValue(context.getPrimaryEntityInstance());

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
