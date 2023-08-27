package simulation.definition;

import entity.definition.EntityDefinition;
import expression.impl.property.AbstractPropertyExpression;
import property.definition.PropertyDefinition;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SimulationDefinition implements Serializable {
    private final Map<String, EntityDefinition> entitiesDef;
    private final Map<String, PropertyDefinition> environmentsDef;
    private final List<Rule> rules;
    private final Termination termination;


    public SimulationDefinition(Map<String, EntityDefinition> entitiesDef, Map<String, PropertyDefinition> environmentsDef, List<Rule> rules, Termination termination) {
        this.entitiesDef = entitiesDef;
        this.environmentsDef = environmentsDef;
        this.rules = rules;
        this.termination = termination;
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
