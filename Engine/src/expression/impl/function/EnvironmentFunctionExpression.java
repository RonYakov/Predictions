package expression.impl.function;

import entity.instance.EntityInstance;
import expression.ExpressionType;

import java.util.stream.Stream;

import static utills.helperFunction.Helper.environment;

public class EnvironmentFunctionExpression extends AbstractFunctionExpression {
    private final String environmentName;

    public EnvironmentFunctionExpression(String value, String environmentName,ExpressionType expressionType) {
        super(value, expressionType);
        this.environmentName = environmentName;
    }

    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity) {
        String environmentVarValue = environment(environmentName);
        return environmentVarValue;
    }
}
