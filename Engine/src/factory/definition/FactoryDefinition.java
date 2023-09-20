package factory.definition;

import entity.definition.EntityDefinition;
import exception.DuplicateNameException;
import exception.NumberNotInRangeException;
import exception.PropertyTypeException;
import factory.action.ActionCreator;
import factory.expression.ExpressionCreator;
import grid.Grid;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import property.definition.PropertyDefinition;
import property.definition.range.Range;
import property.definition.value.PropertyDefinitionValue;
import rule.Rule;
import rule.action.api.Action;
import rule.activation.Activation;
import schema.generated.*;
import simulation.definition.SimulationDefinition;
import termination.Termination;

import java.util.*;

import static factory.action.ActionCreator.createAction;

public abstract class FactoryDefinition {

    public static SimulationDefinition createSimulationDefinition(PRDWorld prdWorld) {
        Map<String, EntityDefinition> entityDefinitionMap = createEntitiesDefinition(prdWorld.getPRDEntities());
        Map<String, PropertyDefinition> environmentsMap = createEnvironments(prdWorld.getPRDEnvironment());
        List<Rule> ruleList = createRules(prdWorld.getPRDRules());
        Grid grid = createGrid(prdWorld.getPRDGrid());
        Termination termination = createTermination(prdWorld.getPRDTermination());

        Integer numOfThreads = prdWorld.getPRDThreadCount();
        return new SimulationDefinition(entityDefinitionMap, environmentsMap, grid, ruleList, termination,numOfThreads);
    }

    private static Grid createGrid(PRDWorld.PRDGrid prdGrid) {
        if(prdGrid.getRows() >= 10 && prdGrid.getRows() <= 100) {
            if(prdGrid.getColumns() >= 10 && prdGrid.getColumns() <= 100) {
                return new Grid(prdGrid.getRows(), prdGrid.getColumns());
            }
            throw new RuntimeException("GridSizeException! Columns must be between 10 and 100.");
        }
        throw new RuntimeException("GridSizeException! Rows must be between 10 and 100.");
    }

    private static PropertyDefinition createPropertyDefinition(PRDProperty prdProperty) {
        PropertyDefinitionValue value = new PropertyDefinitionValue(prdProperty.getPRDValue().isRandomInitialize(),prdProperty.getPRDValue().getInit());
        String type = new String(prdProperty.getType());
        String name = new String(prdProperty.getPRDName());

        Range range = null;
        if(type.equals("decimal") || type.equals("float")) {
            range = new Range(prdProperty.getPRDRange().getFrom(), prdProperty.getPRDRange().getTo());
        }

        if(!type.equals("decimal") && !type.equals("boolean") && !type.equals("float") && !type.equals("string")) {
            throw new PropertyTypeException("PropertyTypeException: " + prdProperty.getPRDName() + " type is not valid!" + " Problem occurred in class FactoryDefinition");
        }
        if(!value.isRandomInitialize()) {
            switch(type) {
                case "decimal":
                    try {
                        if(!isInRange(Integer.parseInt(value.getInit()), range)) {
                            throw new NumberNotInRangeException("NumberNotInRangeException: When trying to create property '" + prdProperty.getPRDName() + "'.\n" +
                                    "Please enter decimal number between " + range.getFrom() + " - " + range.getTo() + ". \nProblem occurred in class FactoryDefinition");
                        }
                    }
                    catch (NumberFormatException e) {
                        throw new NumberFormatException("NumberFormatException: When trying to create property '" + prdProperty.getPRDName() + "'" +
                                "Please enter decimal number in init. Problem occurred in class FactoryDefinition");
                    }
                    break;

                case "float":
                    try {
                        if(!isInRange(Float.parseFloat(value.getInit()), range)) {
                            throw new NumberNotInRangeException("NumberNotInRangeException: When trying to create property '" + prdProperty.getPRDName() + "'.\n" +
                                    "Please enter float number between " + range.getFrom() + " - " + range.getTo() + ". \nProblem occurred in class FactoryDefinition");
                        }
                    }
                    catch (NumberFormatException e) {
                        throw new NumberFormatException( "NumberFormatException: When trying to create property '" + prdProperty.getPRDName() + "'" +
                                "Please enter float number in init. Problem occurred in class FactoryDefinition");
                    }
                    break;

                case "boolean":
                        if(!value.getInit().equals("true") && !value.getInit().equals("false")) {
                            throw new IllegalArgumentException( "IllegalArgumentException: When trying to create property '" + prdProperty.getPRDName() + "'" +
                                    "Please enter 'true' / 'false' in init. Problem occurred in class FactoryDefinition");
                        }
                    break;

                //case String - everything goes
            }
        }

        return new PropertyDefinition(name, type,value,range);
    }

    private static EntityDefinition createEntityDefinition(PRDEntity PRDEntity) {
        String name = new String(PRDEntity.getName());
        List<PRDProperty> PRDProperties = PRDEntity.getPRDProperties().getPRDProperty();
        Map<String, PropertyDefinition> properties = new HashMap<>();
        String duplicateName = FindDuplicateNamesForProp(PRDProperties);

        if(duplicateName != null) {
            throw new DuplicateNameException("DuplicateNameException: the property name '" + duplicateName + "' in entity '" + name + "' show more then once.\n" +
                    "Note that every property in a single entity must have a unique name! \nProblem occurred in class FactoryDefinition");
        }

        PRDProperties.forEach(prdProperty -> properties.put(prdProperty.getPRDName(),createPropertyDefinition(prdProperty)));

        return new EntityDefinition(name ,properties);
    }

    private static Map<String, EntityDefinition> createEntitiesDefinition(PRDEntities prdEntities) {
        Map<String, EntityDefinition> res = new HashMap<>();
        List<PRDEntity> prdEntityList = prdEntities.getPRDEntity();
        String duplicateName = FindDuplicateNamesForEnt(prdEntityList);

        if(duplicateName != null) {
            throw new DuplicateNameException("DuplicateNameException: the entity name '" + duplicateName + "' show more then once.\n" +
                    "Note that every entity must have a unique name! Problem occurred in class FactoryDefinition");
        }
        prdEntityList.forEach(prdEntity -> res.put(prdEntity.getName(), createEntityDefinition(prdEntity)));

        ActionCreator.setEntityDefinitionMap(res);
        ExpressionCreator.setPropertiesOfEntities(createPropOfEntitiesMap(res));
        ExpressionCreator.setEntityDefinition(res);
        return res;
    }

    private static PropertyDefinition createEnvironmentPropDefinition(PRDEnvProperty prdEnvProperty) {
        String type = new String(prdEnvProperty.getType());
        String name = new String(prdEnvProperty.getPRDName());

        Range range = null;
        if(type.equals("decimal") || type.equals("float")) {
            if(prdEnvProperty.getPRDRange() != null) {
                range = new Range(prdEnvProperty.getPRDRange().getFrom(), prdEnvProperty.getPRDRange().getTo());
            }
        }

        if(!type.equals("decimal") && !type.equals("boolean") && !type.equals("float") && !type.equals("string")) {
            throw new PropertyTypeException("PropertyTypeException: " + name + " type is not valid!\n" +
                    "Note that environment type must be: 'decimal', 'boolean', 'float' or 'string'. \nProblem occurred in class FactoryDefinition");
        }

        return new PropertyDefinition(name, type, null, range);
    }

    private static Map<String, PropertyDefinition> createEnvironments(PRDEnvironment prdEnvironment) {
        Map<String, PropertyDefinition> res = new HashMap<>();
        List<PRDEnvProperty> prdEnvironmentList = prdEnvironment.getPRDEnvProperty();
        String duplicateName = FindDuplicateNamesForEnvironment(prdEnvironmentList);

        if(duplicateName != null) {
            throw new DuplicateNameException("DuplicateNameException: the environment name '" + duplicateName + "' show more then once.\n" +
                    "Note that every environment must have a unique name! \nProblem occurred in class FactoryDefinition");
        }
        prdEnvironmentList.forEach(prdEnvProperty -> res.put(prdEnvProperty.getPRDName(),createEnvironmentPropDefinition(prdEnvProperty)));

        ExpressionCreator.setEnvironmentsDefinition(res);
        return res;
    }

    private static List<Action> createActions(PRDActions prdActions) {
        List<Action> res = new ArrayList<>();
        List<PRDAction> actionList = prdActions.getPRDAction();

        for (PRDAction prdAction : actionList) {
            res.add(createAction(prdAction));
        }
        return res;
    }

    private static Activation createActivation(PRDActivation prdActivation) {
        if(prdActivation != null) {
            Integer ticks = prdActivation.getTicks();
            Double probability = prdActivation.getProbability();
            return new Activation(ticks, probability);
        }
        else {
            return new Activation();
        }

    }

    private static Rule createRule(PRDRule prdRule) {
        Activation activation = createActivation(prdRule.getPRDActivation());
        List<Action> actionList = createActions(prdRule.getPRDActions());

        return new Rule(prdRule.getName(), activation, actionList);
    }

    private static List<Rule> createRules(PRDRules prdRules) {
        List<Rule> res = new ArrayList<>();
        List<PRDRule> rulesList = prdRules.getPRDRule();

        for (PRDRule prdRule : rulesList) {
            res.add(createRule(prdRule));
        }
        return res;
    }

    private static Termination createTermination(PRDTermination prdTermination){
        Integer seconds = null, ticks = null;
        Boolean byUser;

        if(prdTermination.getPRDBySecondOrPRDByTicks().size() == 1) {
            if(prdTermination.getPRDBySecondOrPRDByTicks().get(0) instanceof PRDBySecond){
                seconds = ((PRDBySecond)prdTermination.getPRDBySecondOrPRDByTicks().get(0)).getCount();
            } else {
                ticks = ((PRDByTicks)prdTermination.getPRDBySecondOrPRDByTicks().get(0)).getCount();
            }
            byUser = false;
        }
        else  if(prdTermination.getPRDBySecondOrPRDByTicks().size() == 2) {
            if(prdTermination.getPRDBySecondOrPRDByTicks().get(0) instanceof PRDBySecond) {
                seconds = ((PRDBySecond)prdTermination.getPRDBySecondOrPRDByTicks().get(0)).getCount();
                ticks = ((PRDByTicks)prdTermination.getPRDBySecondOrPRDByTicks().get(1)).getCount();
            }
            else {
                ticks = ((PRDByTicks)prdTermination.getPRDBySecondOrPRDByTicks().get(0)).getCount();
                seconds = ((PRDBySecond)prdTermination.getPRDBySecondOrPRDByTicks().get(1)).getCount();
            }
            byUser = false;
        }
        else {
            byUser = true;
        }
        return new Termination(ticks,seconds,byUser);
    }

    private static boolean isInRange(double numberToCheck, Range range) {
        if(range == null) {
            return true;
        }
        if(numberToCheck >= range.getFrom() && numberToCheck <= range.getTo()) {
            return true;
        }
        return false;
    }

    private static String FindDuplicateNamesForProp(List<PRDProperty> prdPropertyList) {
        Set<String> seenNames = new HashSet<>();

        for (PRDProperty prdProperty : prdPropertyList) {
            String name = prdProperty.getPRDName();
            if (seenNames.contains(name)) {
                return name;
            }
            seenNames.add(name);
        }

        return null;
    }

    private static String FindDuplicateNamesForEnt(List<PRDEntity> prdEntityList) {
        Set<String> seenNames = new HashSet<>();

        for (PRDEntity prdProperty : prdEntityList) {
            String name = prdProperty.getName();
            if (seenNames.contains(name)) {
                return name;
            }
            seenNames.add(name);
        }

        return null;
    }

    private static String FindDuplicateNamesForEnvironment(List<PRDEnvProperty> prdEnvPropertyList) {
        Set<String> seenNames = new HashSet<>();

        for (PRDEnvProperty prdEnvProperty : prdEnvPropertyList) {
            String name = prdEnvProperty.getPRDName();
            if (seenNames.contains(name)) {
                return name;
            }
            seenNames.add(name);
        }

        return null;
    }

    private static Map<String, PropertyDefinition> createPropOfEntitiesMap(Map<String, EntityDefinition> entityDefinitionMap) {
        Map<String, PropertyDefinition> res = new HashMap<>();

        for (Map.Entry<String, EntityDefinition> entry : entityDefinitionMap.entrySet()) {
            entry.getValue().getProperties().forEach((s, propertyDefinition) -> res.put(s,propertyDefinition));
        }
        return res;
    }
}
