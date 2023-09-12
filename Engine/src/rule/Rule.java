package rule;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import entity.instance.EntityState;
import grid.Grid;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.context.impl.ActionContextImpl;
import rule.action.impl.condition.enums.ConditionResult;
import rule.activation.Activation;
import simulation.impl.SimulationExecutionDetails;

import java.io.Serializable;
import java.util.*;

public class Rule implements Serializable {
    private String name;
    private Activation activation;
    private List<Action> actions;
    private ActionContext context;
    private SimulationExecutionDetails simulationExecutionDetails;

    public Rule(String name, Activation activation, List<Action> actions) {
        this.name = name;
        this.activation = activation;
        this.actions = actions;
        context = new ActionContextImpl();
    }

    public Rule(Rule rule) {
        name = new String(rule.getName());
        activation = new Activation(rule.activation);
        actions = rule.getActions();
        context = new ActionContextImpl();
        simulationExecutionDetails = null;
    }

    public void activate(Map<String, EntityInstanceManager> entityInstanceManagerMap , Grid grid) {
        actions.forEach(action -> runAction(action, entityInstanceManagerMap , grid));
    }

    public String getName() {
        return name;
    }

    public Activation getActivation() {
        return activation;
    }

    public void setSimulationExecutionDetails(SimulationExecutionDetails simulationExecutionDetails) {
        this.simulationExecutionDetails = simulationExecutionDetails;
    }

    private void runAction(Action action , Map<String, EntityInstanceManager> entityInstanceManagerMap , Grid grid) {
        String entityMangerName = action.getPrimaryEntityDefinition().getName();
        EntityInstanceManager primaryEntity =  entityInstanceManagerMap.get(entityMangerName);
        Integer count;
        context.setPrimaryEntityInstance(null);
        context.setEntityManager(primaryEntity);
        context.setRows(grid.getRows());
        context.setCols(grid.getCols());
        context.setEnvironments(simulationExecutionDetails.getEnvironments());
        List<EntityInstance> secondaryEntitiesFiltered = getFilteredSecondaryEntityList(action, entityInstanceManagerMap);
        Collections.shuffle(secondaryEntitiesFiltered);

        if(action.getSecondaryEntity() != null){
            if(action.getSecondaryEntity().getCount() == null){
                count = secondaryEntitiesFiltered.size();
            }
            else {
                if(secondaryEntitiesFiltered.size() < action.getSecondaryEntity().getCount()) {
                    count = secondaryEntitiesFiltered.size();
                }
                else {
                    count = action.getSecondaryEntity().getCount();
                }
            }

            context.setSecondaryEntityName(action.getSecondaryEntity().getEntityName());

            for (EntityInstance entityInstance : primaryEntity.getEntityInstanceList()) {
                context.setPrimaryEntityInstance(entityInstance);
                context.setStopAction(false);
                if(secondaryEntitiesFiltered.size() > 0 ){
                    for(int i  = 0; i < count ; i++){
                        context.setSecondaryEntity(secondaryEntitiesFiltered.get(i));
                        if(context.getStopAction()){
                            break;
                        }
                        action.Invoke(context);
                    }
                } else {
                    action.Invoke(context);
                }
            }
        } else {
            context.setSecondaryEntityName(null);

            for (EntityInstance entityInstance : primaryEntity.getEntityInstanceList()) {
                context.setPrimaryEntityInstance(entityInstance);
                action.Invoke(context);
            }
        }

        if(context.getPrimaryEntityInstance() != null) {
            entityReplacement(action,context, entityInstanceManagerMap, grid);

            entityKiller(primaryEntity);
        }
    }

    private void entityReplacement(Action action ,ActionContext context, Map<String, EntityInstanceManager> entityInstanceManagerMap, Grid grid) {
        List<EntityInstance> primary = new ArrayList<>();


        for (EntityInstance entityInstance : entityInstanceManagerMap.get(context.getPrimaryEntityInstance().getEntType()).getEntityInstanceList()) {
            if(entityInstance.getState() != EntityState.REPLACE) {
                primary.add(entityInstance);
            }else {
                if(grid.getEntity(entityInstance.getGridIndex()) == entityInstance) {
                    EntityInstance newOne = entityInstance.replaceMe(entityInstanceManagerMap.get(context.getSecondaryEntityInstance().getEntType()));
                    entityInstanceManagerMap.get(context.getSecondaryEntityName()).getEntityInstanceList().add(newOne);
                    grid.replaceEntities(newOne, entityInstance.getGridIndex());
                }
            }
        }

        entityInstanceManagerMap.get(context.getPrimaryEntityInstance().getEntType()).setEntityInstanceList(primary);
    }

    private List<EntityInstance> getFilteredSecondaryEntityList(Action action, Map<String, EntityInstanceManager> entityInstanceManagerMap) {
        List<EntityInstance> secondaryEntitiesFiltered = new ArrayList<>();
        ActionContext context = new ActionContextImpl();

        if(action.getSecondaryEntity() == null) {
            return new ArrayList<>();
        }

        EntityInstanceManager secondaryEntityInstance = entityInstanceManagerMap.get(action.getSecondaryEntity().getEntityName());

        for (EntityInstance ent : secondaryEntityInstance.getEntityInstanceList())  {
            context.setPrimaryEntityInstance(ent);
            if(action.getSecondaryEntity().getCondition() == null){
                secondaryEntitiesFiltered.add(ent);
            } else if(action.getSecondaryEntity().getCondition().runCondition(context) == ConditionResult.TRUE){
                secondaryEntitiesFiltered.add(ent);
            }
        }

        return secondaryEntitiesFiltered;
    }

    public List<Action> getActions() {
        return actions;
    }

    public int numOfActions(){
        return actions.size();
    }
    private void entityKiller(EntityInstanceManager primaryEntity) {
        List<EntityInstance> res = new ArrayList<>();

        for (EntityInstance entityInstance : primaryEntity.getEntityInstanceList()) {
            if(entityInstance.getState() != EntityState.KILL) {
                res.add(entityInstance);
            }
        }

        primaryEntity.setEntityInstanceList(res);
    }

    public boolean isActivatable(int currTick) {
        Random random = new Random();

        if(currTick % activation.getTicks() == 0 && random.nextFloat() < activation.getProbability()) {
            return true;
        } else {
            return false;
        }
    }
}
