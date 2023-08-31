package rule.action.impl.condition;

import entity.definition.EntityDefinition;
import exception.ExpressionTypeException;
import exception.PropertyTypeException;
import expression.ExpressionType;
import expression.api.Expression;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.MultipleConditionDTO;
import option2.ActionDTO.SingleConditionDTO;
import property.definition.PropertyDefinition;
import property.definition.PropertyType;
import rule.action.ActionType;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;
import rule.action.impl.condition.enums.OperatorType;

import java.util.List;

import static utills.string.StringConvertor.convertStringToFloat;

public class SingleCondition extends AbstractCondition {
    private Expression property;
    private Expression value;
    private OperatorType operator;

    public SingleCondition(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition ,List<Action> then, List<Action> elsE, Expression property, Expression value, OperatorType operator) {
        super(primaryEntityDefinition,secondaryEntityDefinition ,ActionType.SINGLE_CONDITION, then, elsE);
        this.property = property;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntityDefinition() == null) {
            return new SingleConditionDTO("Single Condition", getPrimaryEntityDefinition().getName(), null,
                    property.GetSimpleValue(), value.GetSimpleValue(), operator.toString(), thenAmount(), elseAmount());
        }
        return new SingleConditionDTO("Single Condition", getPrimaryEntityDefinition().getName(), getSecondaryEntityDefinition().getName(),
                property.GetSimpleValue(), value.GetSimpleValue(), operator.toString(), thenAmount(), elseAmount());
    }

    @Override
    protected boolean runCondition(ActionContext context) {
        switch (operator){
            case EQUAL:
                return equalCondition(context);
            case UNEQUAL:
                return !equalCondition(context);
            case BT:
                return btCondition(context);
            default:
                return ltCondition(context);
        }
    }

    private boolean ltCondition(ActionContext context) {
        checkIfNumberExpression();
        checkIfNumberProperty(context);
        float propVal = convertStringToFloat(property.GetExplicitValue(context.getPrimaryEntityInstance()));
        float expVal = convertStringToFloat(value.GetExplicitValue(context.getPrimaryEntityInstance()));

        return propVal < expVal;
    }
    private boolean btCondition(ActionContext context) {
        checkIfNumberExpression();
        checkIfNumberProperty(context);
        float propVal = convertStringToFloat(property.GetExplicitValue(context.getPrimaryEntityInstance()));
        float expVal = convertStringToFloat(value.GetExplicitValue(context.getPrimaryEntityInstance()));

        return propVal > expVal;
    }

    private boolean equalCondition(ActionContext context) {
        String propValue = property.GetExplicitValue(context.getPrimaryEntityInstance());
        String expValue = value.GetExplicitValue(context.getPrimaryEntityInstance());

        if(isANumberProp(context) && isANumberExp()){
            return convertStringToFloat(propValue) == convertStringToFloat(expValue);
        } else if (isABoolProp(context) && isABoolExp()){
            return propValue.equalsIgnoreCase(expValue);
        } else if (isAStringProp(context) && isAStringExp()) {
            return propValue.equals(expValue);
        }else{
            throw new PropertyTypeException("PropertyTypeException: can not try equaling '" + property.GetSimpleValue() + "' and '" + value.GetSimpleValue() + "'.\n" +
                    "       Note that you can only equal number to number, string to string or boolean to boolean." +
                    "       Exception thrown while trying to do equal in SingleCondition class");
        }
    }

    private boolean isANumberProp(ActionContext context){
        return property.getType() == ExpressionType.FLOAT || property.getType() == ExpressionType.INT;
    }
    private boolean isABoolProp(ActionContext context){
        return property.getType() == ExpressionType.BOOLEAN;
    }
    private boolean isAStringProp(ActionContext context){
        return property.getType() == ExpressionType.STRING;
    }
    private boolean isANumberExp(){
        return value.getType() == ExpressionType.FLOAT || value.getType() == ExpressionType.INT;
    }
    private boolean isABoolExp(){
        return value.getType() == ExpressionType.BOOLEAN;
    }
    private boolean isAStringExp(){
        return value.getType() == ExpressionType.STRING;
    }

    private void checkIfNumberProperty(ActionContext context) {
        if(!isANumberProp(context)) {
            throw new PropertyTypeException("PropertyTypeException: the property '" + property + "is not a numeric property!\n" +
                    "       Note that lt/bt operators in single-condition action has to get numeric arguments.\n" +
                    "       Problem occurred in class SingleCondition when trying to run single-condition action");
        }
    }
    private void checkIfNumberExpression() {
        if(!isANumberExp()) {
            throw new ExpressionTypeException("ExpressionTypeException: the expression '" + value.GetSimpleValue() + "' is not a number!\n" +
                    "       Note that lt/bt operators in single-condition action has to get numeric arguments.\n" +
                    "       Problem occurred in class SingleCondition when trying to run single-condition action");
        }
    }
}
