package simulation.impl;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import grid.Grid;
import property.instance.AbstractPropertyInstance;
import rule.Rule;
import rule.action.context.api.ActionContext;
import rule.action.context.impl.ActionContextImpl;
import simulation.api.EnvironmentsSimulation;
import termination.Termination;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static utills.helperFunction.Helper.setCurrentSimulation;

public class Simulation implements EnvironmentsSimulation , Serializable {
    private Map<String, EntityInstanceManager> entityManager;
    private final Map<String, AbstractPropertyInstance> environments;
    private final List<Rule> rules;
    private final Grid grid;
    private final Termination termination;
    private String formattedDate;
    private final int identifyNumber;
    private String simulationStopCause;
    public Simulation(Map<String, EntityInstanceManager> entityManager, Map<String, AbstractPropertyInstance> environments,Grid grid, List<Rule> rules, Termination termination, int identifyNumber) {
        this.entityManager = entityManager;
        this.environments = environments;
        this.grid = grid;
        this.rules = rules;
        this.termination = termination;
        this.identifyNumber = identifyNumber;
        this.simulationStopCause = null;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        formattedDate = now.format(formatter);
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public int getIdentifyNumber() {
        return identifyNumber;
    }

    public Map<String, EntityInstanceManager> getEntityManager() {
        return entityManager;
    }

    public String getSimulationStopCause() {
        return simulationStopCause;
    }

    public AbstractPropertyInstance getEnvironment(String environmentName) {
        return environments.get(environmentName);
    }

    public Map<String, AbstractPropertyInstance> getEnvironments() {
        return environments;
    }

    public void runSimulation() {
        setCurrentSimulation(this); //this is the first thing you do when running the simulation
        long startTime = System.currentTimeMillis();
        long maxRuntimeMilliseconds;
        Integer ticks = termination.getTicks();
        Integer seconds = termination.getSeconds();
        Integer currTick = 1;


        grid.setGrid(new LinkedList<>(entityManager.values()));

        if(ticks != null && seconds != null){
            maxRuntimeMilliseconds = seconds * 1000;

            for(; currTick <= ticks ; currTick++){
                if (System.currentTimeMillis() - startTime >= maxRuntimeMilliseconds) {
                    simulationStopCause = "Time";
                    break;
                }
                simulationIteration(currTick);
                if(currTick.equals(ticks)) {
                    simulationStopCause = "Ticks";
                }
                updateTicks();
                moveEntities();
            }
        } else if (ticks == null && seconds !=null) {
            boolean timesUp = false;
            maxRuntimeMilliseconds = seconds * 1000;

            while (!timesUp){
                if (System.currentTimeMillis() - startTime >= maxRuntimeMilliseconds) {
                    timesUp = true;
                    simulationStopCause = "Time";
                    break;
                }
                simulationIteration(currTick);
                currTick++;
                updateTicks();
            }
        }else {
            for(; currTick <= ticks ; currTick++){
                simulationIteration(currTick);
                if(currTick.equals(ticks)) {
                    simulationStopCause = "Ticks";
                }
                updateTicks();
            }
        }
    }

    private void moveEntities() {
        List<EntityInstanceManager> managerList = new ArrayList<>(entityManager.values());

        for (EntityInstanceManager entityInstanceManager: managerList) {
            for (EntityInstance entityInstance:entityInstanceManager.getEntityInstanceList()) {
                grid.moveEntity(entityInstance);
            }
        }
    }

    private void updateTicks(){
        List<EntityInstanceManager> managerList = new ArrayList<>(entityManager.values());

        for (EntityInstanceManager entityInstanceManager: managerList) {
            for (EntityInstance entityInstance:entityInstanceManager.getEntityInstanceList()) {
                List<AbstractPropertyInstance> propertyInstances = new ArrayList<>(entityInstance.getProperties().values());
                for (AbstractPropertyInstance abstractPropertyInstance: propertyInstances) {
                    if(abstractPropertyInstance.isModified()){
                        abstractPropertyInstance.setModified(false);
                    }else {
                        abstractPropertyInstance.setTicks(abstractPropertyInstance.getTicks() + 1);
                    }
                }
            }
        }
    }

    private void simulationIteration(Integer currTick) {
        for (Rule rule : rules) {
            if(rule.isActivatable(currTick)){
                rule.activate(entityManager , grid);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simulation that = (Simulation) o;
        return identifyNumber == that.identifyNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifyNumber);
    }
}
