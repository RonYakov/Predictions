package expression.impl.property;

import entity.instance.EntityInstance;
import exception.PropertyNotFoundException;
import exception.SecondEntityIgnoreException;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

import java.util.Map;

public class FloatPropertyExpression extends AbstractPropertyExpression {
    public FloatPropertyExpression(String value) {
        super(value, ExpressionType.FLOAT);
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity, Map<String, AbstractPropertyInstance> environments, Boolean isSeconderyShouldExist){
        String res = primaryEntity.getSpecificPropertyValue(getValue());
        if(res == null) {
            if (secondaryEntity != null) {
                res = secondaryEntity.getSpecificPropertyValue(getValue());
            }
            else if(isSeconderyShouldExist == true) {
                throw new SecondEntityIgnoreException();
            }

            if(res == null) {
                throw new PropertyNotFoundException("PropertyNotFoundException: " + getValue() + "was not found!\n" +
                        "       Please make sure the property you enter to an action is in the right entity. Problem occurred in class FloatPropertyExpression");
            }
        }
        return res;
    }
}
