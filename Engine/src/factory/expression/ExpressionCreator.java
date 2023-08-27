package factory.expression;

import exception.EnvironmentInputException;
import exception.RandomInputException;
import expression.ExpressionType;
import expression.api.Expression;
import expression.impl.function.EnvironmentFunctionExpression;
import expression.impl.function.RandomFunctionExpression;
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
        else if (input.startsWith("environment(") && input.endsWith(")")) {
            String environmentInput = input.substring(12, input.length() - 1);
            isAnEnvironment(environmentInput); // function that will throw exception if not!
            ExpressionType type = getEnvironmentExpressionType(environmentInput);

            return new EnvironmentFunctionExpression(input,environmentInput,type);
        }
        else {
            return null;
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

    private static int isAnInteger(String randomInput) {
        try {
            return convertStringToInt(randomInput);
        } catch (Exception exception) {
            throw new RandomInputException("RandomInputException: the following input " + randomInput + " is not a valid input to function random.\n" +
                    "       Note that random has to get a single integer! Problem occurred in class ExpressionCreator");
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

    private static void isAnEnvironment(String environmentInput) {
        PropertyDefinition environmentDef = environmentsDefinition.get(environmentInput);

        if(environmentDef == null) {
            throw  new EnvironmentInputException("EnvironmentInputException: the following input '" + environmentInput + "' is not a valid input to function environment.\n" +
                    "       Note that environment has to get a single environment's name! Problem occurred in class ExpressionCreator");
        }
    }
}
