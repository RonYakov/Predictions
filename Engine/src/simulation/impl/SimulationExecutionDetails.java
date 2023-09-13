package simulation.impl;

import entity.instance.EntityInstanceManager;
import grid.Grid;
import manager.PredictionManager;
import property.instance.AbstractPropertyInstance;
import rule.Rule;
import termination.Termination;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class SimulationExecutionDetails {
    private Map<String, EntityInstanceManager> entityManager;
    private final Map<String, AbstractPropertyInstance> environments;
    private final List<Rule> rules;
    private final Grid grid;
    private final Termination termination;
    private String formattedDate;
    private final int identifyNumber;
    private String simulationStopCause;
    private Integer currTicks;
    private Integer seconds;
    private SimulationState simulationState;
    private volatile boolean isRunning = true;
    private PredictionManager predictionManager;

    public SimulationExecutionDetails(Map<String, EntityInstanceManager> entityManager, Map<String, AbstractPropertyInstance> environments,Grid grid, List<Rule> rules, Termination termination, int identifyNumber) {
        this.entityManager = entityManager;
        this.environments = environments;
        this.grid = grid;
        this.rules = rules;
        this.termination = termination;
        this.identifyNumber = identifyNumber;
        this.simulationStopCause = null;
        this.simulationState = null;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        formattedDate = now.format(formatter);

        for (Rule rule: rules) {
            rule.setSimulationExecutionDetails(this);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;
    }

    public PredictionManager getPredictionManager() {
        return predictionManager;
    }

    public Integer getCurrTicks() {
        return currTicks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public SimulationState getSimulationState() {
        return simulationState;
    }

    public void setSimulationState(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    public void setCurrTicks(Integer currTicks) {
        this.currTicks = currTicks;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Map<String, EntityInstanceManager> getEntityManager() {
        return entityManager;
    }

    public Map<String, AbstractPropertyInstance> getEnvironments() {
        return environments;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Grid getGrid() {
        return grid;
    }

    public Termination getTermination() {
        return termination;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public Integer getIdentifyNumber() {
        return identifyNumber;
    }

    public String getSimulationStopCause() {
        return simulationStopCause;
    }

    public void setEntityManager(Map<String, EntityInstanceManager> entityManager) {
        this.entityManager = entityManager;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public void setSimulationStopCause(String simulationStopCause) {
        this.simulationStopCause = simulationStopCause;
    }
}
