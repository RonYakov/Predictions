package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public class StringExpression extends AbstractValueExpression {
    public StringExpression(String value) {
        super(value, ExpressionType.STRING);
    }

    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity) {
        return getValue();
    }
}
