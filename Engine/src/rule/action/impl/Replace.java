package rule.action.impl;

import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import option2.ActionDTO.KillDTO;
import option2.ActionDTO.ProximityDTO;
import option2.ActionDTO.ReplaceDTO;
import rule.action.ActionType;
import rule.action.api.Action;
import rule.action.context.api.ActionContext;

public class Replace extends AbstractAction {
    private String mode;

    public Replace(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition, String mode) {
        super(primaryEntityDefinition, secondaryEntityDefinition, ActionType.REPLACE);
        this.mode = mode;
    }

    @Override
    public ActionDTO createDTO() {
        if(getSecondaryEntityDefinition() == null) {
            return new ReplaceDTO("Replace", getPrimaryEntityDefinition().getName(), null, mode);

        }
        return new ReplaceDTO("Replace", getPrimaryEntityDefinition().getName(), getSecondaryEntityDefinition().getName(), mode);
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void Invoke(ActionContext context) {
        //todo
    }


}
