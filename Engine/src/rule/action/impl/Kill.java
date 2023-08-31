package rule.action.impl;

import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.IncreaseDTO;
import option2.ActionDTO.KillDTO;
import option2.ActionDTO.MultipleConditionDTO;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;

public class Kill extends AbstractAction {
    public Kill(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition) {
        super(primaryEntityDefinition, secondaryEntityDefinition ,ActionType.KILL);
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntityDefinition() == null) {
            return new KillDTO("Kill", getPrimaryEntityDefinition().getName(), null);
        }
        return new KillDTO("Kill", getPrimaryEntityDefinition().getName(), getSecondaryEntityDefinition().getName());
    }

    @Override
    public void Invoke(ActionContext context) {
        context.getPrimaryEntityInstance().killMe();
    }
}
