package expression.api;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

import java.util.Map;

public interface Expression {
    String GetSimpleValue();

    String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity, Map<String, AbstractPropertyInstance> environments);
    ExpressionType getType();
    String getEntityName();
}
