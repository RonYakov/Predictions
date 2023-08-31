package factory.expression;

import entity.definition.EntityDefinition;
import exception.EnvironmentInputException;
import exception.ExpressionTypeException;
import exception.RandomInputException;
import expression.ExpressionType;
import expression.api.Expression;
import expression.impl.function.*;
import expression.impl.property.BooleanPropertyExpression;
import expression.impl.property.DecimalPropertyExpression;
import expression.impl.property.FloatPropertyExpression;
import expression.impl.property.StringPropertyExpression;
import expression.impl.value.BooleanExpression;
import expression.impl.value.DecimalExpression;
import expression.impl.value.FloatExpression;
import expression.impl.value.StringExpression;
import property.definition.PropertyDefinition;

import java.util.Map;

import static utills.string.StringConvertor.*;

public abstract class ExpressionCreator {
    private static Map<String, PropertyDefinition> propertiesOfEntities;
    private static Map<String, PropertyDefinition> environmentsDefinition;

    private static Map<String, EntityDefinition> entityDefinition;

    public static Expression createExpression(String input) {
        Expression res;

        res = createFunctionExpression(input);
        if(res != null) {
            return res;
        }

        res = createPropertyExpression(input);
        if(res != null) {
            return res;
        }

        res = createValueExpression(input);
        return res;
    }

    private static Expression createFunctionExpression(String input) {
        if (input.startsWith("random(") && input.endsWith(")")) {
            String randomInput = input.substring(7, input.length() - 1);
            int randomArg = isAnInteger(randomInput);

            return new RandomFunctionExpression(input,randomArg);
        }
        else if (input.startsWith("evaluate(") && input.endsWith(")")) {
            String evaluateInput = input.substring(9, input.length() - 1);
            String[] helper = evaluateInput.split("\\.");
            isValidInput(helper, evaluateInput);
            EntityDefinition entityDefinition = isAnEntity(helper[0]);
            isValidProperty(entityDefinition, helper[1]);
            ExpressionType type = getPropertyExpressionType(entityDefinition.getProperty(helper[1]));

            return new EvaluateFunctionExpression(input, type, entityDefinition.getName(), helper[1]);

        }else if (input.startsWith("percent(") && input.endsWith(")")) {
            String percentInput = input.substring(8, input.length() - 1);
            String[] helper = percentInput.split(",");
            isValidInput(helper, percentInput);

            Expression arg1 = ExpressionCreator.createExpression(helper[0]);
            Expression arg2 = ExpressionCreator.createExpression(helper[1]);

            isExpressionANumber(arg1);
            isExpressionANumber(arg2);

            return new PercentFunctionExpression(input,arg1,arg2);
        }else if (input.startsWith("ticks(") && input.endsWith(")")) {
            String ticksInput = input.substring(6, input.length() - 1);
            String[] helper = ticksInput.split("\\.");
            isValidInput(helper, ticksInput);
            EntityDefinition entityDefinition = isAnEntity(helper[0]);
            isValidProperty(entityDefinition, helper[1]);
            ExpressionType type = getPropertyExpressionType(entityDefinition.getProperty(helper[1]));

            return new TicksFunctionExpression(input, type, entityDefinition.getName(), helper[1]);

        }else if (input.startsWith("environment(") && input.endsWith(")")) {
            String environmentInput = input.substring(12, input.length() - 1);
            isAnEnvironment(environmentInput);
            ExpressionType type = getEnvironmentExpressionType(environmentInput);

            return new EnvironmentFunctionExpression(input,environmentInput,type);
        }
        else {
            return null;
        }
    }

    private static void isExpressionANumber(Expression expression) {
        if(expression.getType() != ExpressionType.INT && expression.getType() != ExpressionType.FLOAT) {
            throw new ExpressionTypeException("ExpressionTypeException! the expression: " + expression.GetSimpleValue() + " is not a number!");
        }
    }

    private static void isValidProperty(EntityDefinition entityDefinition, String property) {
        if(entityDefinition.getProperty(property) == null){
            throw  new EnvironmentInputException("InputException: the following input '" + property + "' is not a property in "+ entityDefinition.getName() + ".\n" +
                    "       Note that the input must be: <entity>.<property> ! Problem occurred in class ExpressionCreator");
        }
    }

    private static void isValidInput(String[] helper, String WholeInput) {
        if(helper.length != 2) {
            throw  new EnvironmentInputException("InputException: the following input '" + WholeInput + "' is not valid.\n" +
                    "       ! Problem occurred in class ExpressionCreator");
        }
    }

    private static Expression createPropertyExpression(String input) {
        PropertyDefinition propertyDef = propertiesOfEntities.get(input);

        if (propertyDef == null) {
            return null;
        }

        switch (propertyDef.getType()) {
            case DECIMAL:
                return new DecimalPropertyExpression(input);
            case FLOAT:
                return new FloatPropertyExpression(input);
            case BOOLEAN:
                return new BooleanPropertyExpression(input);
            default:
                return new StringPropertyExpression(input);
        }
    }

    private static Expression createValueExpression(String input) {
        if(isStringInt(input)) {
            return new DecimalExpression(input);
        }
        if(isStringFloat(input)) {
            return new FloatExpression(input);
        }
        if(isStringBoolean(input)) {
            return new BooleanExpression(input);
        }

        return new StringExpression((input));
    }

    public static void setPropertiesOfEntities(Map<String, PropertyDefinition> propertiesOfEntities) {
        ExpressionCreator.propertiesOfEntities = propertiesOfEntities;
    }

    public static void setEnvironmentsDefinition(Map<String, PropertyDefinition> environmentsDefinition) {
        ExpressionCreator.environmentsDefinition = environmentsDefinition;
    }

    public static void setEntityDefinition(Map<String, EntityDefinition> entityDefinition) {
        ExpressionCreator.entityDefinition = entityDefinition;
    }

    private static int isAnInteger(String randomInput) {
        try {
            return convertStringToInt(randomInput);
        } catch (Exception exception) {
            throw new RandomInputException("RandomInputException: the following input " + randomInput + " is not a valid input .\n" +
                    "       Note that you must enter a number! Problem occurred in class ExpressionCreator");
        }
    }

    private static ExpressionType getEnvironmentExpressionType(String environmentInput) {
        PropertyDefinition environmentDef = environmentsDefinition.get(environmentInput);

        switch (environmentDef.getType()) {
            case DECIMAL:
                return ExpressionType.INT;
            case FLOAT:
                return ExpressionType.FLOAT;
            case BOOLEAN:
                return ExpressionType.BOOLEAN;
            default:
                return ExpressionType.STRING;
            }
        }
    private static ExpressionType getPropertyExpressionType(PropertyDefinition propertyDefinition) {
        switch (propertyDefinition.getType()) {
            case DECIMAL:
                return ExpressionType.INT;
            case FLOAT:
                return ExpressionType.FLOAT;
            case BOOLEAN:
                return ExpressionType.BOOLEAN;
            default:
                return ExpressionType.STRING;
        }
    }

    private static void isAnEnvironment(String environmentInput) {
        PropertyDefinition environmentDef = environmentsDefinition.get(environmentInput);

        if(environmentDef == null) {
            throw  new EnvironmentInputException("EnvironmentInputException: the following input '" + environmentInput + "' is not a valid input to function environment.\n" +
                    "       Note that environment has to get a single environment's name! Problem occurred in class ExpressionCreator");
        }
    }
    private static EntityDefinition isAnEntity(String entityInput) {
        EntityDefinition environmentDef = entityDefinition.get(entityInput);

        if(environmentDef == null) {
            throw  new EnvironmentInputException("EntityInputException: the following input '" + entityInput + "' is not a entity.\n" +
                    "       Note that evaluate has to get a <entity>.<property> ! Problem occurred in class ExpressionCreator");
        }
        return environmentDef;
    }
}
