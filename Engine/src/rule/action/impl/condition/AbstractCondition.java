package rule.action.impl.condition;


import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import rule.action.ActionType;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;
import rule.action.impl.secondaryEntity.SecondaryEntity;

import java.util.List;

public abstract class AbstractCondition extends AbstractAction {
    private List<Action> Then;
    private List<Action> Else;

    public AbstractCondition(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition, ActionType type, List<Action> Then, List<Action> Else) {
        super(primaryEntityDefinition, secondaryEntityDefinition ,type);
        this.Then = Then;
        this.Else = Else;
    }

    protected Integer thenAmount() {
        return Then.size();
    }

    protected Integer elseAmount() {
        return Else.size();
    }

    protected void invokeThen(ActionContext context) {
        if (Then != null) {
            Then.forEach(action -> action.Invoke(context));
        }
    }

    protected void invokeElse(ActionContext context) {
        if (Else != null) {
            Else.forEach(action -> action.Invoke(context));
        }
    }

    public abstract boolean runCondition(ActionContext context);

    @Override
    public void Invoke(ActionContext context) {
        if(runCondition(context)){
            invokeThen(context);
        }else {
            invokeElse(context);
        }
    }
}
