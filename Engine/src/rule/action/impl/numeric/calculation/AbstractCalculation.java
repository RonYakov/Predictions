package rule.action.impl.numeric.calculation;

import entity.definition.EntityDefinition;
import expression.api.Expression;
import rule.action.ActionType;
import rule.action.impl.AbstractAction;
import rule.action.impl.numeric.AbstractNumericAction;

public abstract class AbstractCalculation extends AbstractNumericAction {
    private Expression firstArgument;
    private Expression secondArgument;

    public AbstractCalculation(EntityDefinition primaryEntityDefinition, ActionType type, String resultProp, Expression firstArgument, Expression secondArgument) {
        super(primaryEntityDefinition, type, resultProp);
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }

    public Expression getFirstArgument() {
        return firstArgument;
    }

    public Expression getSecondArgument() {
        return secondArgument;
    }
}
