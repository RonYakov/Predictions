package expression.impl.property;

import entity.instance.EntityInstance;
import exception.PropertyNotFoundException;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

public class FloatPropertyExpression extends AbstractPropertyExpression {
    public FloatPropertyExpression(String value) {
        super(value, ExpressionType.FLOAT);
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity){
        String res = primaryEntity.getSpecificPropertyValue(getValue());
        if(res == null) {
            if (secondaryEntity != null) {
                res = secondaryEntity.getSpecificPropertyValue(getValue());
            }

            if(res == null) {
                throw new PropertyNotFoundException("PropertyNotFoundException: " + getValue() + "was not found!\n" +
                        "       Please make sure the property you enter to an action is in the right entity. Problem occurred in class FloatPropertyExpression");
            }
        }
        return res;
    }
}
