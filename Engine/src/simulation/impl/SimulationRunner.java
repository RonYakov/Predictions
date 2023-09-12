package simulation.impl;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import property.instance.AbstractPropertyInstance;
import rule.Rule;

import java.io.Serializable;
import java.util.*;

public class SimulationRunner implements Serializable, Runnable {
    private SimulationExecutionDetails simulationExecutionDetails;
    public SimulationRunner(SimulationExecutionDetails simulationExecutionDetails) {
        this.simulationExecutionDetails = simulationExecutionDetails;
    }

    public String getFormattedDate() {
        return simulationExecutionDetails.getFormattedDate();
    }

    public int getIdentifyNumber() {
        return simulationExecutionDetails.getIdentifyNumber();
    }

    public Map<String, EntityInstanceManager> getEntityManager() {
        return simulationExecutionDetails.getEntityManager();
    }

    public String getSimulationStopCause() {
        return simulationExecutionDetails.getSimulationStopCause();
    }

    public Map<String, AbstractPropertyInstance> getEnvironments() {
        return simulationExecutionDetails.getEnvironments();
    }

    @Override
    public void run() {
        simulationExecutionDetails.setSimulationState(SimulationState.RUNNING);
        System.out.println("running thread: " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        long maxRuntimeMilliseconds;
        Integer ticks = simulationExecutionDetails.getTermination().getTicks();
        Integer seconds = simulationExecutionDetails.getTermination().getSeconds();
        Integer currTick = 1;


        simulationExecutionDetails.getGrid().setGrid(new LinkedList<>(simulationExecutionDetails.getEntityManager().values()));

        if(ticks != null && seconds != null){
            maxRuntimeMilliseconds = seconds * 1000;

            for(; currTick <= ticks ; currTick++){
                if (System.currentTimeMillis() - startTime >= maxRuntimeMilliseconds || simulationExecutionDetails.getSimulationState().equals(SimulationState.STOPPED)) {
                    simulationExecutionDetails.setSimulationStopCause("Time");
                    //todo stop cause
                    break;
                }
                simulationIteration(currTick);
                if(currTick.equals(ticks)) {
                    simulationExecutionDetails.setSimulationStopCause("Ticks");
                }
                updateTicks();
                moveEntities();
                simulationExecutionDetails.setCurrTicks(currTick);
                simulationExecutionDetails.setSeconds((int)((System.currentTimeMillis() - startTime) / 1000));
            }
        } else if (ticks == null && seconds != null) {
            boolean timesUp = false;
            maxRuntimeMilliseconds = seconds * 1000;

            while (!timesUp){
                if (System.currentTimeMillis() - startTime >= maxRuntimeMilliseconds || simulationExecutionDetails.getSimulationState().equals(SimulationState.STOPPED)) {
                    timesUp = true;
                    simulationExecutionDetails.setSimulationStopCause("Time");
                    break;
                }
                simulationIteration(currTick);
                currTick++;
                updateTicks();
                moveEntities();
                simulationExecutionDetails.setCurrTicks(currTick);
                simulationExecutionDetails.setSeconds((int)((System.currentTimeMillis() - startTime) / 1000));
            }
        }else {
            for(; currTick <= ticks && !(simulationExecutionDetails.getSimulationState().equals(SimulationState.STOPPED)); currTick++){
                simulationIteration(currTick);
                if(currTick.equals(ticks)) {
                    simulationExecutionDetails.setSimulationStopCause("Ticks");
                }
                updateTicks();
                moveEntities();
                simulationExecutionDetails.setCurrTicks(currTick);
                simulationExecutionDetails.setSeconds((int)((System.currentTimeMillis() - startTime) / 1000));
            }
            //todo need to add another loop for null in both ticks and seconds
        }

        simulationExecutionDetails.setSimulationState(SimulationState.STOPPED);
    }

    private void moveEntities() {
        List<EntityInstanceManager> managerList = new ArrayList<>(simulationExecutionDetails.getEntityManager().values());

        for (EntityInstanceManager entityInstanceManager: managerList) {
            for (EntityInstance entityInstance:entityInstanceManager.getEntityInstanceList()) {
                simulationExecutionDetails.getGrid().moveEntity(entityInstance);
            }
        }
    }

    private void updateTicks(){
        List<EntityInstanceManager> managerList = new ArrayList<>(simulationExecutionDetails.getEntityManager().values());

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
        for (Rule rule : simulationExecutionDetails.getRules()) {
            if(rule.isActivatable(currTick)){
                rule.activate(simulationExecutionDetails.getEntityManager() , simulationExecutionDetails.getGrid());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationRunner that = (SimulationRunner) o;
        return simulationExecutionDetails.getIdentifyNumber() == that.simulationExecutionDetails.getIdentifyNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(simulationExecutionDetails.getIdentifyNumber());
    }
}
