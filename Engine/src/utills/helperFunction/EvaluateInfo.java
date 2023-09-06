package utills.helperFunction;

import expression.ExpressionType;
import property.definition.PropertyType;

public class EvaluateInfo {
    private String value;
    private ExpressionType type;

    public EvaluateInfo(String value, ExpressionType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public ExpressionType getType() {
        return type;
    }
}
