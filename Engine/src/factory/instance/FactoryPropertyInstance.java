package factory.instance;

import property.definition.PropertyDefinition;
import property.definition.range.Range;
import property.instance.AbstractPropertyInstance;
import property.instance.impl.BooleanPropertyInstance;
import property.instance.impl.DecimalPropertyInstance;
import property.instance.impl.FloatPropertyInstance;
import property.instance.impl.StringPropertyInstance;

import java.util.Random;

public abstract class FactoryPropertyInstance {

    public static AbstractPropertyInstance createPropertyInstance(PropertyDefinition propertyDefinition) {

        switch (propertyDefinition.getType()) {
            case DECIMAL:
                return createDecimalPropInstance(propertyDefinition);
            case FLOAT:
                return createFloatPropInstance(propertyDefinition);
            case BOOLEAN:
                return createBooleanPropInstance(propertyDefinition);
            default:
                return createStringPropInstance(propertyDefinition);
        }
    }

    private static AbstractPropertyInstance createStringPropInstance(PropertyDefinition propertyDefinition) {
        String value = null;
        if(propertyDefinition.getValue() != null) {
            if(propertyDefinition.getValue().isRandomInitialize()) {
                value = randomizeString();
            }
            else {
                value = propertyDefinition.getValue().getInit();
            }
        }

        return new StringPropertyInstance(propertyDefinition.getName(), value);
    }
    private static String randomizeString() {
            String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!?,_-.() ";
            int length = new Random().nextInt(50) + 1;
            StringBuilder randomString = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int randomIndex = new Random().nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                randomString.append(randomChar);
            }

            return randomString.toString();
    }

    private static AbstractPropertyInstance createBooleanPropInstance(PropertyDefinition propertyDefinition) {
        Boolean value = null;
        if(propertyDefinition.getValue() != null) {
            if (propertyDefinition.getValue().isRandomInitialize()) {
                value = randomizeBoolean();
            } else {
                value = Boolean.parseBoolean(propertyDefinition.getValue().getInit());
            }
        }

        return new BooleanPropertyInstance(value, propertyDefinition.getName());
    }
    private static boolean randomizeBoolean() {
        int helper = new Random().nextInt(2) + 1;

        return helper % 2 == 0;
    }

    private static AbstractPropertyInstance createFloatPropInstance(PropertyDefinition propertyDefinition) {
        Float value = null;
        if(propertyDefinition.getValue() != null) {
            if (propertyDefinition.getValue().isRandomInitialize()) {
                value = randomizeFloat(propertyDefinition.getRange().getFrom(), propertyDefinition.getRange().getTo());
            } else {
                value = Float.parseFloat(propertyDefinition.getValue().getInit());
            }
        }

        return new FloatPropertyInstance(propertyDefinition.getName(), propertyDefinition.getRange() , value);
    }
    private static float randomizeFloat(double from, double to) {
        Random random = new Random();
        return (float) (from + random.nextDouble() * (to - from));
    }

    private static AbstractPropertyInstance createDecimalPropInstance(PropertyDefinition propertyDefinition) {
        Integer value = null;
        if(propertyDefinition.getValue() != null) {
            if (propertyDefinition.getValue().isRandomInitialize()) {
                value = randomizeInt(propertyDefinition.getRange().getFrom(), propertyDefinition.getRange().getTo());
            } else {
                value = Integer.parseInt(propertyDefinition.getValue().getInit());
            }
        }

        return new DecimalPropertyInstance(propertyDefinition.getName(), propertyDefinition.getRange() , value);
    }
    private static int randomizeInt(double from, double to) {
        Random random = new Random();
        return (int) (from + random.nextDouble() * (to - from));
    }
}
