package expression.impl.function;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import property.instance.AbstractPropertyInstance;

import java.util.Map;

import static utills.helperFunction.Helper.random;

public class RandomFunctionExpression extends AbstractFunctionExpression {
    private final int randomArgument;
    public RandomFunctionExpression(String value , int randomArgument) {
        super(value, ExpressionType.INT);
        this.randomArgument = randomArgument;
    }

    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity, Map<String, AbstractPropertyInstance> environments) {
        return random(randomArgument).toString();
    }
}
