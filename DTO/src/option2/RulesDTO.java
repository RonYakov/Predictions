package option2;

import java.util.List;

public class RulesDTO {
    private String name;
    private Integer ticks;
    private Double probability;
    private Integer actionCounter;
    private List<String> actionTypes;

    public RulesDTO(String name, Integer ticks, Double probability, Integer actionCounter, List<String> actionTypes) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        this.actionCounter = actionCounter;
        this.actionTypes = actionTypes;
    }

    public String getName() {
        return name;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }

    public Integer getActionCounter() {
        return actionCounter;
    }

    public List<String> getActionTypes() {
        return actionTypes;
    }
}
