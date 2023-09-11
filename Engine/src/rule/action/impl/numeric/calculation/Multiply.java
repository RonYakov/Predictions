package rule.action.impl.numeric.calculation;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import expression.ExpressionType;
import expression.api.Expression;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.CalculationDTO;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.secondaryEntity.SecondaryEntity;

import static utills.string.StringConvertor.convertStringToFloat;
import static utills.string.StringConvertor.convertStringToInt;

public class Multiply extends AbstractCalculation {
    public Multiply(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition, String resultProp, Expression firstArgument, Expression secondArgument) {
        super(primaryEntityDefinition, secondaryEntityDefinition,ActionType.MULTIPLY, resultProp, firstArgument, secondArgument);
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntity() == null) {
            return new CalculationDTO("Multiply", getPrimaryEntityDefinition().getName(), null,
                    getFirstArgument().GetSimpleValue(), getSecondArgument().GetSimpleValue(), getResultProp());
        }
        return new CalculationDTO("Multiply", getPrimaryEntityDefinition().getName(), getSecondaryEntity().getEntityName(),
                getFirstArgument().GetSimpleValue(), getSecondArgument().GetSimpleValue(), getResultProp());
    }

    @Override
    public void Invoke(ActionContext context) {
        Number result;
        EntityInstance mainEntity = getEntityForInvoke(context);
        if(mainEntity == null){
            return;
        }
        EntityInstance otherEntity = getOtherEntity(mainEntity, context);
        Boolean isSeconderyShouldExist = true;
        if(otherEntity == null) {
            if(context.getSecondaryEntityName() == null){
                isSeconderyShouldExist = false;
            }
        }

        if(getFirstArgument().getType() == ExpressionType.FLOAT || getSecondArgument().getType() == ExpressionType.FLOAT){
            result = convertStringToFloat(getFirstArgument().GetExplicitValue(mainEntity, otherEntity, context.getEnvironments(), isSeconderyShouldExist)) *
                    convertStringToFloat(getSecondArgument().GetExplicitValue(mainEntity, otherEntity, context.getEnvironments(), isSeconderyShouldExist));
        }
        else{
            result = convertStringToInt(getFirstArgument().GetExplicitValue(mainEntity, otherEntity, context.getEnvironments(), isSeconderyShouldExist)) *
                    convertStringToInt(getSecondArgument().GetExplicitValue(mainEntity, otherEntity, context.getEnvironments(), isSeconderyShouldExist));
        }

        AbstractPropertyInstance propertyInstance = extractProperty(context);

        propertyInstance.setValue(checkIfActionResultIsInRange(result,propertyInstance).toString());
    }
}
