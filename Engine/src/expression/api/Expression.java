package expression.api;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public interface Expression {
    String GetSimpleValue();
    String GetExplicitValue(EntityInstance entity);
    ExpressionType getType();
    String getEntityName();
}
