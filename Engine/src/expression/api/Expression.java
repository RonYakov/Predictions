package expression.api;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public interface Expression {
    String GetSimpleValue();

    String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity);
    ExpressionType getType();
    String getEntityName();
}
