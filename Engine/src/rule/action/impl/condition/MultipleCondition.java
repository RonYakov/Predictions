package rule.action.impl.condition;

import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.MultipleConditionDTO;
import rule.action.ActionType;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.impl.condition.enums.LogicType;
import rule.action.impl.secondaryEntity.SecondaryEntity;

import java.util.List;

public class MultipleCondition extends AbstractCondition {
    private final List<AbstractCondition> conditions;
    private final LogicType logic;

    public MultipleCondition(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition , List<Action> then, List<Action> elsE, List<AbstractCondition> conditions, LogicType logic) {
        super(primaryEntityDefinition,secondaryEntityDefinition ,ActionType.MULTIPLE_CONDITION, then, elsE);
        this.conditions = conditions;
        this.logic = logic;
    }
    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntity() == null) {
            return new MultipleConditionDTO("Multiple Condition", getPrimaryEntityDefinition().getName(), null,
                    logic.toString(), thenAmount(), elseAmount());
        }
        return new MultipleConditionDTO("Multiple Condition", getPrimaryEntityDefinition().getName(), getSecondaryEntity().getEntityName(),
                logic.toString(), thenAmount(), elseAmount());
    }

    @Override
    protected boolean runCondition(ActionContext context) {
        switch (logic){
            case OR:
                return runOrCondition(context);
            case AND:
                return runAndCondition(context);
            default:
                return false;
        }
    }

    private boolean runAndCondition(ActionContext context) {
        for (AbstractCondition condition: conditions) {
            if (!condition.runCondition(context)){
                return false;
            }
        }
        return true;
    }

    private boolean runOrCondition(ActionContext context) {

        for (AbstractCondition condition: conditions) {
            if (condition.runCondition(context)){
                return true;
            }
        }
        return false;
    }

}
