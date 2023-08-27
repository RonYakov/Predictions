package rule.action.impl.numeric;

import entity.definition.EntityDefinition;
import exception.TryToPreformFloatActionOnDecimalPropertyException;
import expression.ExpressionType;
import expression.api.Expression;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;

import static utills.helperFunction.Helper.isDecimal;
import static utills.helperFunction.Helper.isFloat;

public class Decrease extends AbstractNumericAction {

    private final Expression by;

    public Decrease(EntityDefinition primaryEntityDefinition, String resultProp, Expression by) {
        super(primaryEntityDefinition,ActionType.DECREASE, resultProp);
        this.by = by;
    }

    @Override
    public void Invoke(ActionContext context) {
        AbstractPropertyInstance property = extractProperty(context);
        Number newPropertyValue = extractANumber(context);

        if(by.getType() == ExpressionType.INT) {
            if(isDecimal(newPropertyValue.toString())) {
                newPropertyValue = newPropertyValue.intValue() - Integer.parseInt(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            } else {
                newPropertyValue = newPropertyValue.floatValue() - Integer.parseInt(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            }
        }
        else if (by.getType() == ExpressionType.FLOAT) {
            if(isFloat(newPropertyValue.toString())) {
                newPropertyValue = newPropertyValue.floatValue() - Float.parseFloat(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            }
            else {
               throw new TryToPreformFloatActionOnDecimalPropertyException("Float subtraction from Decimal property is not allowed. Error occurred in"
               + this.getClass());
            }
        }

        property.setValue(checkIfActionResultIsInRange(newPropertyValue, property).toString());
    }
}
