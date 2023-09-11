package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

import java.util.Map;

public class FloatExpression extends AbstractValueExpression {
    public FloatExpression(String value) {
        super(value, ExpressionType.FLOAT);
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
