package rule.action.impl;

import entity.definition.EntityDefinition;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;

public class Kill extends AbstractAction {
    public Kill(EntityDefinition primaryEntityDefinition) {
        super(primaryEntityDefinition, ActionType.KILL);
    }

    @Override
    public void Invoke(ActionContext context) {
        context.getPrimaryEntityInstance().killMe();
    }
}
