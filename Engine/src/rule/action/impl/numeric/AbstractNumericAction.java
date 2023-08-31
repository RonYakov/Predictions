package rule.action.impl.numeric;

import entity.definition.EntityDefinition;
import exception.NotRealPropertyException;
import exception.PropertyNotFoundException;
import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;
import rule.action.ActionType;
import rule.action.context.api.ActionContext;
import rule.action.impl.AbstractAction;

import static utills.helperFunction.Helper.isDecimal;

public abstract class AbstractNumericAction extends AbstractAction {
    private final String resultProp;

    public AbstractNumericAction(EntityDefinition primaryEntityDefinition, EntityDefinition secondaryEntityDefinition,ActionType type, String resultProp) {
        super(primaryEntityDefinition, secondaryEntityDefinition ,type);
        this.resultProp = resultProp;
    }

    public String getResultProp() {
        return resultProp;
    }

    protected Number extractANumber(ActionContext context) {
        AbstractPropertyInstance property = extractProperty(context);
        String propertyValue;

        propertyValue = property.getValue();
        if(isDecimal(propertyValue)){
            return Integer.parseInt(propertyValue);
        } else {
            return Float.parseFloat(propertyValue);
        }
    }

    protected AbstractPropertyInstance extractProperty(ActionContext context) {
        AbstractPropertyInstance res = context.getPrimaryEntityInstance().getProperty(resultProp);

        if(res != null) {
            return res;
        }
        else {
            throw new NotRealPropertyException("NotRealPropertyException! The requested property" + resultProp + "does not exist. Occurred " + this.getClass());
        }
    }

//    protected Boolean isANumber(AbstractPropertyInstance property) {
//        String propertyVal = property.getValue();
//        return propertyVal.matches("-?\\d+(\\.\\d+)?");
//
//    }

}
