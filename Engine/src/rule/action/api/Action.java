package rule.action.api;

import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.secondaryEntity.SecondaryEntity;

import java.io.Serializable;

public interface Action  {
    ActionDTO createDTO();
    ActionType getType();
    EntityDefinition getPrimaryEntityDefinition();
    SecondaryEntity getSecondaryEntity();
    void Invoke(ActionContext context);
}
