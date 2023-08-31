package rule.action.impl.numeric;

import entity.definition.EntityDefinition;
import exception.TryToPreformFloatActionOnDecimalPropertyException;
import expression.ExpressionType;
import expression.api.Expression;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.DecreaseDTO;
import option2.ActionDTO.IncreaseDTO;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;


import static utills.helperFunction.Helper.isDecimal;
import static utills.helperFunction.Helper.isFloat;

public class Increase extends AbstractNumericAction {
    private final Expression by;

    public Increase(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition,String resultProp, Expression by) {
        super(primaryEntityDefinition, secondaryEntityDefinition,ActionType.INCREASE, resultProp);
        this.by = by;
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntityDefinition() == null) {
            return new IncreaseDTO("Increase", getPrimaryEntityDefinition().getName(), null,
                    getResultProp(), by.GetSimpleValue());
        }
        return new IncreaseDTO("Increase", getPrimaryEntityDefinition().getName(), getSecondaryEntityDefinition().getName(),
                getResultProp(), by.GetSimpleValue());
    }

    @Override
    public void Invoke(ActionContext context) {
        AbstractPropertyInstance property = extractProperty(context);
        Number newPropertyValue = extractANumber(context);

        if(by.getType() == ExpressionType.INT) {
            if(isDecimal(newPropertyValue.toString())) {
                newPropertyValue = newPropertyValue.intValue() + Integer.parseInt(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            }
            else {
                newPropertyValue = newPropertyValue.floatValue() + Integer.parseInt(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            }
        }
        else if (by.getType() == ExpressionType.FLOAT) {
            if(isDecimal(newPropertyValue.toString())) {
                throw new TryToPreformFloatActionOnDecimalPropertyException("Float add on Decimal property is not allowed. Error occurred in"
                        + this.getClass());
            }
            else {
                newPropertyValue = newPropertyValue.floatValue() + Float.parseFloat(by.GetExplicitValue(context.getPrimaryEntityInstance()));
            }
        }

        property.setValue(checkIfActionResultIsInRange(newPropertyValue, property).toString());
    }
}
