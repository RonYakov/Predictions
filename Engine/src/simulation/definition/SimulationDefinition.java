package simulation.definition;

import entity.definition.EntityDefinition;
import expression.impl.property.AbstractPropertyExpression;
import grid.Grid;
import property.definition.PropertyDefinition;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SimulationDefinition implements Serializable {
    private final Map<String, EntityDefinition> entitiesDef;
    private final Map<String, PropertyDefinition> environmentsDef;
    private final Grid grid;
    private final List<Rule> rules;
    private final Termination termination;
    private Integer numOfThreads;


    public SimulationDefinition(Map<String, EntityDefinition> entitiesDef, Map<String, PropertyDefinition> environmentsDef, Grid grid, List<Rule> rules, Termination termination, Integer numOfThreads) {
        this.entitiesDef = entitiesDef;
        this.environmentsDef = environmentsDef;
        this.grid = grid;
        this.rules = rules;
        this.termination = termination;
        this.numOfThreads = numOfThreads;
    }

    public Integer getNumOfThreads() {
        return numOfThreads;
    }

    public Grid getGrid() {
        return grid;
    }

    public Map<String, EntityDefinition> getEntitiesDef() {
        return entitiesDef;
    }

    public Map<String, PropertyDefinition> getEnvironmentsDef() {
        return environmentsDef;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Termination getTermination() {
        return termination;
    }
}
