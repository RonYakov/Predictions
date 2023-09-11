package expression.impl;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import expression.api.Expression;
import rule.action.context.api.ActionContext;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractExpression implements Expression, Serializable {
    private final String value;
    private ExpressionType type;

    protected String getValue() {
        return value;
    }

    public AbstractExpression(String value, ExpressionType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    protected void setType(ExpressionType type) {
        this.type = type;
    }

    public ExpressionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractExpression that = (AbstractExpression) o;
        return Objects.equals(value, that.value) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
