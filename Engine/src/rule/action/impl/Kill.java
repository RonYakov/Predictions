package rule.action.impl;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.KillDTO;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.secondaryEntity.SecondaryEntity;

public class Kill extends AbstractAction {
    public Kill(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition) {
        super(primaryEntityDefinition, secondaryEntityDefinition ,ActionType.KILL);
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntity() == null) {
            return new KillDTO("Kill", getPrimaryEntityDefinition().getName(), null);
        }
        return new KillDTO("Kill", getPrimaryEntityDefinition().getName(), getSecondaryEntity().getEntityName());
    }

    private EntityInstance getEntityForInvoke(ActionContext context) {
        if(context.getPrimaryEntityInstance().getEntType().equals(getPrimaryEntityDefinition().getName())) {
            return context.getPrimaryEntityInstance();
        }
        else if(context.getSecondaryEntityName() == null) {
            throw new RuntimeException(); //todo think later
        } else if (context.getSecondaryEntityInstance().getEntType().equals(getPrimaryEntityDefinition().getName())) {
            return context.getSecondaryEntityInstance();
        } else if (context.getSecondaryEntityInstance() == null) {
            return null;
        } else {
            throw new RuntimeException(); //todo think later
        }
    }

    @Override
    public void Invoke(ActionContext context) {
        EntityInstance entityInstanceToKill = getEntityForInvoke(context);

        if(entityInstanceToKill != null){
            entityInstanceToKill.killMe();
        }
    }
}
