package expression.impl.function;

import entity.instance.EntityInstance;
import expression.ExpressionType;

public class EvaluateFunctionExpression extends AbstractFunctionExpression {
    String entityName;
    String PropertyName;

    public EvaluateFunctionExpression(String value, ExpressionType type, String entityName, String propertyName) {
        super(value, type);
        this.entityName = entityName;
        PropertyName = propertyName;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }


    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    //todo method
    @Override
    public String GetExplicitValue(EntityInstance entity) {
        return null;
    }
}
