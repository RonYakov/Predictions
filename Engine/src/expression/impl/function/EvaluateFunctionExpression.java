package expression.impl.function;

import entity.instance.EntityInstance;
import expression.ExpressionType;
import utills.helperFunction.EvaluateInfo;

import static utills.helperFunction.Helper.evaluate;

public class EvaluateFunctionExpression extends AbstractFunctionExpression {
    private String entityName;
    private String propertyName;

    public EvaluateFunctionExpression(String value, ExpressionType type, String entityName, String propertyName) {
        super(value, type);
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }


    @Override
    public String GetSimpleValue() {
        return getValue();
    }

    @Override
    public String GetExplicitValue(EntityInstance primaryEntity, EntityInstance secondaryEntity) {
        EvaluateInfo res;

        if(entityName.equals(primaryEntity.getEntType())){
            res = evaluate(primaryEntity,propertyName);
        }else if(secondaryEntity != null){
            if(entityName.equals(secondaryEntity.getEntType())){
               res = evaluate(secondaryEntity,propertyName);
            }else {
                throw new RuntimeException(); //todo
            }
        }
        else {
            throw new RuntimeException();
        }

        this.setType(res.getType());
        return res.getValue();
    }
}
