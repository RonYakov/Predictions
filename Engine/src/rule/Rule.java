package rule;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.context.impl.ActionContextImpl;
import rule.action.impl.Kill;
import rule.action.impl.secondaryEntity.SecondaryEntity;
import rule.activation.Activation;

import java.io.Serializable;
import java.util.*;

public class Rule implements Serializable {
    private String name;
    private Activation activation;
    private List<Action> actions;
    private ActionContext context;

    public Rule(String name, Activation activation, List<Action> actions) {
        this.name = name;
        this.activation = activation;
        this.actions = actions;
        context = new ActionContextImpl();
    }

    public void activate(Map<String, EntityInstanceManager> entityInstanceManagerMap) {
        actions.forEach(action -> runAction(action, entityInstanceManagerMap)); //todo
    }

    public String getName() {
        return name;
    }

    public Activation getActivation() {
        return activation;
    }

    private void runAction(Action action , Map<String, EntityInstanceManager> entityInstanceManagerMap) {
        String entityMangerName = action.getPrimaryEntityDefinition().getName();
        EntityInstanceManager primaryEntity =  entityInstanceManagerMap.get(entityMangerName);
        Integer count;
        context.setEntityManager(primaryEntity);
        List<EntityInstance> secondaryEntitiesFiltered = getFilteredSecondaryEntityList(action, entityInstanceManagerMap);
        Collections.shuffle(secondaryEntitiesFiltered);

        if(action.getSecondaryEntity().getCount() == null){
            count = secondaryEntitiesFiltered.size();
        }
        else {
            count = action.getSecondaryEntity().getCount();
        }
        for(int i  = 0; i < count ; i++){
            context.setSecondaryEntity(secondaryEntitiesFiltered.get(i));

            for (EntityInstance entityInstance : primaryEntity.getEntityInstanceList()) {
                context.setPrimaryEntityInstance(entityInstance);
                action.Invoke(context);
            }
        }

        entityKiller(primaryEntity);

    }

    private List<EntityInstance> getFilteredSecondaryEntityList(Action action, Map<String, EntityInstanceManager> entityInstanceManagerMap) {
        List<EntityInstance> secondaryEntitiesFiltered = new ArrayList<>();
        ActionContext context = new ActionContextImpl();

        EntityInstanceManager secondaryEntityInstance = entityInstanceManagerMap.get(action.getSecondaryEntity().getEntityName());

        for (EntityInstance ent : secondaryEntityInstance.getEntityInstanceList())  {
            context.setPrimaryEntityInstance(ent);
            if(action.getSecondaryEntity().getCondition().runCondition(context)){
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
            if(!entityInstance.getToKill()) {
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
