package rule.action.impl.numeric.calculation;

import entity.definition.EntityDefinition;
import expression.ExpressionType;
import expression.api.Expression;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;

import static utills.string.StringConvertor.convertStringToFloat;
import static utills.string.StringConvertor.convertStringToInt;

public class Multiply extends AbstractCalculation {
    public Multiply(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition,String resultProp, Expression firstArgument, Expression secondArgument) {
        super(primaryEntityDefinition, secondaryEntityDefinition,ActionType.MULTIPLY, resultProp, firstArgument, secondArgument);
    }

    @Override
    public void Invoke(ActionContext context) {
        Number result;

        if(getFirstArgument().getType() == ExpressionType.FLOAT || getSecondArgument().getType() == ExpressionType.FLOAT){
            result = convertStringToFloat(getFirstArgument().GetExplicitValue(context.getPrimaryEntityInstance())) *
                    convertStringToFloat(getSecondArgument().GetExplicitValue(context.getPrimaryEntityInstance()));
        }
        else{
            result = convertStringToInt(getFirstArgument().GetExplicitValue(context.getPrimaryEntityInstance())) *
                    convertStringToInt(getSecondArgument().GetExplicitValue(context.getPrimaryEntityInstance()));
        }

        AbstractPropertyInstance propertyInstance = extractProperty(context);

        propertyInstance.setValue(checkIfActionResultIsInRange(result,propertyInstance).toString());
    }
}
