package expression.impl.function;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import expression.api.Expression;

import static utills.helperFunction.Helper.percent;

public class PercentFunctionExpression extends AbstractFunctionExpression {

    private Expression num1;
    private Expression num2;

    public PercentFunctionExpression(String value, Expression num1, Expression num2) {
        super(value, ExpressionType.FLOAT);
        this.num1 = num1;
        this.num2 = num2;
    }



    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity) {
        return percent(Double.parseDouble(num1.GetExplicitValue(primaryEntity,secondaryEntity)),
                Double.parseDouble(num2.GetExplicitValue(primaryEntity, secondaryEntity))).toString();
    }
}
