package expression.api;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public interface Expression {
    String GetSimpleValue();

    //todo - must give the function also the secondary entity (now the expression can work on both primary and secondary)
    String GetExplicitValue(EntityInstance entity);
    ExpressionType getType();
    String getEntityName();
}
