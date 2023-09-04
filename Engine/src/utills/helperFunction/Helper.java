package utills.helperFunction;

import entity.instance.EntityInstance;
import exception.PropertyNotFoundException;
import property.instance.AbstractPropertyInstance;
import simulation.impl.Simulation;

import java.util.Random;

public abstract class Helper {
    private static Simulation currentSimulation;
    public static Integer random(int value){
        Random random = new Random();

        return random.nextInt(value + 1);
    }

    public static void setCurrentSimulation(Simulation i_CurrentSimulation) {
        currentSimulation = i_CurrentSimulation;
    }

    public static String environment(String name){
        AbstractPropertyInstance environmentVariable = currentSimulation.getEnvironments().get(name);

        if(environmentVariable == null){
            throw new PropertyNotFoundException("PropertyNotFoundException " + name + " was not found!" + " Problem occurred when running helper function 'environment' ");
        }

        return environmentVariable.getValue();
    }

    public static Boolean isDecimal(String toCheck) {
        try{
            Integer.parseInt(toCheck);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
    public static Boolean isFloat(String toCheck) {
        try{
            Float.parseFloat(toCheck);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static Double percent(Double whole, Double percentFromWhole){
        if (percentFromWhole < 0 || percentFromWhole > 100) {
            throw new IllegalArgumentException("Percentage should be between 0 and 100.");
        }

        return (percentFromWhole / 100) * whole;
    }

    public static Integer ticks(EntityInstance entityInstance, String propertyName){
        AbstractPropertyInstance abstractPropertyInstance = entityInstance.getProperty(propertyName);

        return abstractPropertyInstance.getTicks();
    }
}
