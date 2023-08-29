package factory.action;

import entity.definition.EntityDefinition;
import exception.*;
import expression.ExpressionType;
import expression.api.Expression;
import property.definition.PropertyDefinition;
import property.definition.PropertyType;
import rule.action.api.Action;
import rule.action.impl.Kill;
import rule.action.impl.Set;
import rule.action.impl.condition.AbstractCondition;
import rule.action.impl.condition.MultipleCondition;
import rule.action.impl.condition.SingleCondition;
import rule.action.impl.condition.enums.LogicType;
import rule.action.impl.condition.enums.OperatorType;
import rule.action.impl.numeric.Decrease;
import rule.action.impl.numeric.Increase;
import rule.action.impl.numeric.calculation.Divide;
import rule.action.impl.numeric.calculation.Multiply;
import schema.generated.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static factory.expression.ExpressionCreator.createExpression;

public abstract class ActionCreator {
    private static Map<String, EntityDefinition> entityDefinitionMap;

    public static Action createAction(PRDAction prdAction) {
        String type = prdAction.getType();

        switch (type) {
            case "increase":
                return createIncrease(prdAction);
            case "decrease":
                return createDecrease(prdAction);
            case "calculation":
                return createCalculation(prdAction);
            case "condition":
                return createCondition(prdAction);
            case "set":
                return createSet(prdAction);
            case "kill":
                return createKill(prdAction);
            default:
                throw new TypeUnmatchedException("TypeUnmatchedException: can not create action with type '" + type +"'.\n" +
                        "       Please enter a real action type. Problem occurred in class ActionCreator.");
        }
    }

    private static Action createIncrease(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "increase", prdAction.getEntity());

        PropertyDefinition propertyDef = entityDef.getProperty(prdAction.getProperty());
        checkIfPropertyExist(entityDef.getName() ,propertyDef,"increase", prdAction.getProperty());
        checkIfNumberProperty(propertyDef, "increase");

        Expression expression = createExpression(prdAction.getBy());
        checkIfNumberExpression(expression,"increase");

        return new Increase(entityDef, null,prdAction.getProperty(), expression);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }

    private static Action createDecrease(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "decrease", prdAction.getEntity());

        PropertyDefinition propertyDef = entityDef.getProperty(prdAction.getProperty());
        checkIfPropertyExist(entityDef.getName(), propertyDef,"decrease", prdAction.getProperty());
        checkIfNumberProperty(propertyDef, "decrease");

        Expression expression = createExpression(prdAction.getBy());
        checkIfNumberExpression(expression,"decrease");

        return new Decrease(entityDef, null,prdAction.getProperty(), expression);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }

    private static Action createCalculation(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "calculation", prdAction.getEntity());

        PropertyDefinition propertyDef = entityDef.getProperty(prdAction.getResultProp());
        checkIfPropertyExist(entityDef.getName(), propertyDef,"calculation", prdAction.getResultProp());
        checkIfNumberProperty(propertyDef, "calculation");

        String desireCalc = checkIfMultiplyOrDivide(prdAction);
        switch (desireCalc) {
            case "multiply":
                return createMultiply(entityDef, propertyDef.getName(), prdAction.getPRDMultiply());
            default:
                return createDivide(entityDef, propertyDef.getName(), prdAction.getPRDDivide());
        }
    }
    private static Action createMultiply(EntityDefinition entityDef, String propertyName, PRDMultiply prdMultiply) {
        Expression argument1 = createExpression(prdMultiply.getArg1());
        checkIfNumberExpression(argument1,"multiply");

        Expression argument2 = createExpression(prdMultiply.getArg2());
        checkIfNumberExpression(argument2,"multiply");

        return new Multiply(entityDef, null,propertyName, argument1, argument2);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }
    private static Action createDivide(EntityDefinition entityDef, String propertyName, PRDDivide prdDivide) {
        Expression argument1 = createExpression(prdDivide.getArg1());
        checkIfNumberExpression(argument1,"divide");

        Expression argument2 = createExpression(prdDivide.getArg2());
        checkIfNumberExpression(argument2,"divide");

        return new Divide(entityDef, null,propertyName, argument1, argument2);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }

    private static Action createCondition(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "condition", prdAction.getEntity());

        PRDCondition prdCondition = prdAction.getPRDCondition();
        return createConditionHelper(prdAction, prdCondition,entityDef);
    }
    private static AbstractCondition createConditionHelper(PRDAction prdAction, PRDCondition prdCondition, EntityDefinition entityDef) {
        String singularity = prdCondition.getSingularity();
        switch (singularity) {
            case "single":
                return createSingleCondition(prdAction, entityDef, prdCondition);
            case "multiple":
                return createMultipleCondition(prdAction, entityDef, prdCondition);
            default:
                throw new NotRealSingularityException("NotRealSingularityException: the singularity '" + singularity + "is not valid!\n" +
                        "       Note that singularity must be 'single' or 'multiple'. Problem occurred in class ActionCreator when trying to create condition action");
        }
    }
    private static AbstractCondition createSingleCondition(PRDAction prdAction, EntityDefinition entityDef, PRDCondition prdCondition) {
        PropertyDefinition propertyDef = entityDef.getProperty(prdCondition.getProperty());
        checkIfPropertyExist(entityDef.getName(), propertyDef,"Single-Condition", prdCondition.getProperty());

        Expression value = createExpression(prdCondition.getValue());

        OperatorType operatorType = getOperatorFromString(prdCondition.getOperator());

        List<Action> Else = null;
        List<Action> Then = null;
        if(prdAction != null) {
            PRDThen prdThen = prdAction.getPRDThen();
            Then = extractActionListFromPRD(prdThen.getPRDAction());

            PRDElse prdElse = prdAction.getPRDElse();
            if(prdElse != null) {
                Else = extractActionListFromPRD(prdElse.getPRDAction());
            }
        }

        return new SingleCondition(entityDef, null ,Then, Else, propertyDef.getName(), value, operatorType);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity
    }
    private static AbstractCondition createMultipleCondition(PRDAction prdAction, EntityDefinition entityDef, PRDCondition prdCondition) {
        LogicType logicType = getLogicalFromString(prdCondition.getLogical());

        List<AbstractCondition> conditionList = extractConditionListFromPRD(prdCondition.getPRDCondition(), entityDef);

        List<Action> Else = null;
        List<Action> Then = null;
        if(prdAction != null) {
            PRDThen prdThen = prdAction.getPRDThen();
            Then = extractActionListFromPRD(prdThen.getPRDAction());

            PRDElse prdElse = prdAction.getPRDElse();
            if(prdElse!= null){
                Else = extractActionListFromPRD(prdElse.getPRDAction());
            }
        }

        return new MultipleCondition(entityDef, null,Then, Else, conditionList, logicType);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }

    private static Action createSet(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "set", prdAction.getEntity());

        PropertyDefinition propertyDef = entityDef.getProperty(prdAction.getProperty());
        checkIfPropertyExist(entityDef.getName() ,propertyDef,"set", prdAction.getProperty());

        Expression value = createExpression(prdAction.getValue());

        return new Set(entityDef, null,propertyDef.getName(), value);
        //todo - change it after we load the new scheme cause now we dont yet have secondary entity

    }

    private static Action createKill(PRDAction prdAction) {
        EntityDefinition entityDef = entityDefinitionMap.get(prdAction.getEntity());
        checkIfEntityExist(entityDef, "kill", prdAction.getEntity());

        return new Kill(entityDef , null); //todo - change it after we load the new scheme cause now we dont yet have secondary entity
    }

    public static void setEntityDefinitionMap(Map<String, EntityDefinition> entityDefinitionMap) {
        ActionCreator.entityDefinitionMap = entityDefinitionMap;
    }
    private static void checkIfEntityExist(EntityDefinition entityDef, String desireAction, String entityName) {
        if(entityDef == null) {
            throw new NotRealEntityException("NotRealEntityException: the entity name '" + entityName + "' does not exist.\n" +
                    "       Note that every action need to get a real entity name! Problem occurred in class ActionCreator when trying to create " + desireAction + " action");
        }
    }
    private static void checkIfPropertyExist(String entityName, PropertyDefinition propertyDef, String desireAction, String propertyName) {
        if(propertyDef == null) {
            throw new PropertyNotFoundException("PropertyNotFoundException: the property name '" + propertyName + "' does not exist in entity '" + entityName + "'.\n" +
                    "       Note that every action need to get a valid property name! Problem occurred in class ActionCreator when trying to create " + desireAction + " action");
        }
    }
    private static void checkIfNumberProperty(PropertyDefinition propertyDef, String desireAction) {
        if(propertyDef.getType() != PropertyType.DECIMAL && propertyDef.getType() != PropertyType.FLOAT) {
            throw new PropertyTypeException("PropertyTypeException: the property '" + propertyDef.getName() + "is not a numeric property!\n" +
                    "       Note that " + desireAction + "  action has to get numeric arguments. Problem occurred in class ActionCreator when trying to create " + desireAction + "  action");
        }
    }
    private static void checkIfNumberExpression(Expression expression, String desireAction) {
        if(expression.getType() != ExpressionType.INT && expression.getType() != ExpressionType.FLOAT){
            throw new ExpressionTypeException("ExpressionTypeException: the expression '" + expression.GetSimpleValue() + "' is not a number!\n" +
                    "       Note that " + desireAction + " action has to get numeric arguments.\n" +
                    "       Problem occurred in class ActionCreator when trying to create " + desireAction + " action");
        }
    }
    private static String checkIfMultiplyOrDivide(PRDAction prdAction) {
        if(prdAction.getPRDMultiply() == null) {
            return "divide";
        }
        else {
            return "multiply";
        }
    }
    private static OperatorType getOperatorFromString(String operator) {
        switch (operator) {
            case "=":
                return OperatorType.EQUAL;
            case "!=":
                return OperatorType.UNEQUAL;
            case "bt":
                return OperatorType.BT;
            case "lt":
                return OperatorType.LT;
            default:
                throw new OperatorTypeException("OperatorTypeException: the operator '"+ operator +"' is not valid.\n" +
                        "       Note that operator for single-condition must be one of the following: '=', '!=', 'bt', 'lt'!\n" +
                        "       Problem occurred in class ActionCreator when trying to create single-condition action");
        }
    }
    private static LogicType getLogicalFromString(String logical) {
        switch (logical) {
            case "or":
                return LogicType.OR;
            case "and":
                return LogicType.AND;
            default:
                throw new LogicalTypeException("LogicalTypeException: the logical '"+ logical +"' is not valid.\n" +
                        "       Note that logical for multiple-condition must be one of the following: 'or', 'and'! Problem occurred in class ActionCreator when trying to create multiple-condition action");
        }
    }
    private static List<Action> extractActionListFromPRD(List<PRDAction> prdActionList) {
        List<Action> res = new ArrayList<>();

        for (PRDAction prdAction : prdActionList) {
            res.add(createAction(prdAction));
        }

        return res;
    }
    private static List<AbstractCondition> extractConditionListFromPRD(List<PRDCondition> prdConditionList, EntityDefinition entityDef) {
        List<AbstractCondition> res = new ArrayList<>();

        for (PRDCondition prdCondition : prdConditionList) {
            res.add(createConditionHelper(null, prdCondition, entityDef));
        }

        return res;
    }

}
