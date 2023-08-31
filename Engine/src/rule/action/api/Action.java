package rule.action.api;

import entity.definition.EntityDefinition;
import option2.ActionDTO.ActionDTO;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;

import java.io.Serializable;

public interface Action  {
    ActionDTO createDTO();
    ActionType getType();
    EntityDefinition getPrimaryEntityDefinition();
    void Invoke(ActionContext context);
}
