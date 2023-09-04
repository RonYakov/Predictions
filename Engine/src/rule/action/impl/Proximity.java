package rule.action.impl;

import entity.definition.EntityDefinition;
import expression.api.Expression;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.ProximityDTO;
import rule.action.ActionType;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;
import rule.action.impl.secondaryEntity.SecondaryEntity;

import java.util.List;

public class Proximity extends AbstractAction {

   private Expression of;
   private List<Action> actionList;

    public Proximity(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntityDefinition, Expression of, List<Action> actionList) {
        super(primaryEntityDefinition, secondaryEntityDefinition, ActionType.PROXIMITY);
        this.of = of;
        this.actionList = actionList;
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntity() == null) {
            return new ProximityDTO("Proximity", getPrimaryEntityDefinition().getName(), null,
                    of.GetSimpleValue(), Integer.toString(actionList.size()));
        }
        return new ProximityDTO("Proximity", getPrimaryEntityDefinition().getName(), getSecondaryEntity().getEntityName(),
                of.GetSimpleValue(), Integer.toString(actionList.size()));
    }

    @Override
    public void Invoke(ActionContext context) {
        //todo
    }
}
