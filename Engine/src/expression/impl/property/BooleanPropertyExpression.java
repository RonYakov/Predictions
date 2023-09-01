package expression.impl.property;

import entity.instance.EntityInstance;
import exception.PropertyNotFoundException;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

public class BooleanPropertyExpression extends AbstractPropertyExpression {
    public BooleanPropertyExpression(String value) {
        super(value, ExpressionType.BOOLEAN);
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
                        "       Please make sure the property you enter to an action is in the right entity. Problem occurred in class BooleanPropertyExpression");
            }
        }
        return res;
    }
}
