package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public class FloatExpression extends AbstractValueExpression {
    public FloatExpression(String value) {
        super(value, ExpressionType.FLOAT);
    }

    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance entity) {
        return getValue();
    }
}
