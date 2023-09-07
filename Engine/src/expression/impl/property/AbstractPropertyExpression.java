package expression.impl.property;

import expression.ExpressionType;
import expression.impl.AbstractExpression;

public abstract class AbstractPropertyExpression extends AbstractExpression {
    public AbstractPropertyExpression(String value, ExpressionType type) {
        super(value, type);
    }

    public String GetSimpleValue() {
        return getValue();
    }

}
