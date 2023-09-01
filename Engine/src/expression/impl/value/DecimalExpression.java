package expression.impl.value;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public class DecimalExpression extends AbstractValueExpression {
    public DecimalExpression(String value) {
        super(value, ExpressionType.INT);
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
