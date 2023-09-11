package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

import java.util.Map;

public class BooleanExpression extends AbstractValueExpression {
    public BooleanExpression(String value) {
        super(value, ExpressionType.BOOLEAN);
    }

    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity, Map<String, AbstractPropertyInstance> environments, Boolean isSeconderyShouldExist) {
        return getValue();
    }
}
