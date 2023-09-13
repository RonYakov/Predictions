package utills.helperFunction;

import entity.instance.EntityInstance;
import exception.PropertyNotFoundException;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;
import simulation.impl.SimulationRunner;

import java.util.Map;
import java.util.Random;

public abstract class Helper {

    public synchronized static Integer random(int value){
        Random random = new Random();

        return random.nextInt(value + 1);
    }

    public synchronized static String environment(String name, Map<String, AbstractPropertyInstance> environments){
        AbstractPropertyInstance environmentVariable = environments.get(name);

        if(environmentVariable == null){
            throw new PropertyNotFoundException("PropertyNotFoundException " + name + " was not found!" + " Problem occurred when running helper function 'environment' ");
        }

        return environmentVariable.getValue();
    }

    public synchronized static Boolean isDecimal(String toCheck) {
        try{
            Integer.parseInt(toCheck);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
    public synchronized static Boolean isFloat(String toCheck) {
        try{
            Float.parseFloat(toCheck);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public synchronized static Double percent(Double whole, Double percentFromWhole){
        if (percentFromWhole < 0 || percentFromWhole > 100) {
            throw new IllegalArgumentException("Percentage should be between 0 and 100.");
        }

        return (percentFromWhole / 100) * whole;
    }

    public synchronized static Integer ticks(EntityInstance entityInstance, String propertyName){
        AbstractPropertyInstance abstractPropertyInstance = entityInstance.getProperty(propertyName);

        return abstractPropertyInstance.getTicks();
    }

    public synchronized static EvaluateInfo evaluate(EntityInstance entityInstance, String propertyName){
        if(entityInstance.getProperty(propertyName) == null){
            throw new RuntimeException("Evaluate Exception! Cannot evaluate the property: " + propertyName + " from entity: " + entityInstance.getEntType());
        }

        ExpressionType expressionType;

        switch (entityInstance.getProperty(propertyName).getType()){
            case DECIMAL:
                expressionType = ExpressionType.INT;
                break;
            case FLOAT:
                expressionType = ExpressionType.FLOAT;
                break;
            case BOOLEAN:
                expressionType = ExpressionType.BOOLEAN;
                break;
            default:
                expressionType = ExpressionType.STRING;
                break;
        }


        return new EvaluateInfo(entityInstance.getProperty(propertyName).getValue(), expressionType);
    }
}
