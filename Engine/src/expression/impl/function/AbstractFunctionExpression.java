package expression.impl.function;

import expression.ExpressionType;
import expression.impl.AbstractExpression;

public abstract class AbstractFunctionExpression extends AbstractExpression {
    public AbstractFunctionExpression(String value, ExpressionType type) {
        super(value, type);
    }
}
