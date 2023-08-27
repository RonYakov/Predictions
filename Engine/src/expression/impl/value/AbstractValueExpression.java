package expression.impl.value;

import expression.ExpressionType;
import expression.impl.AbstractExpression;

public abstract class AbstractValueExpression extends AbstractExpression {
    public AbstractValueExpression(String value, ExpressionType type) {
        super(value, type);
    }
}
