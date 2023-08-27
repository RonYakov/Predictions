package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public class BooleanExpression extends AbstractValueExpression {
    public BooleanExpression(String value) {
        super(value, ExpressionType.BOOLEAN);
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
