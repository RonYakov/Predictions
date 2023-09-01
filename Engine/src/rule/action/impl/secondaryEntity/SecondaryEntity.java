package rule.action.impl.secondaryEntity;

import rule.action.impl.condition.AbstractCondition;

import java.util.List;

public class SecondaryEntity {
    private String entityName;
    private Integer count;
    AbstractCondition condition;

    public SecondaryEntity(String entityName, Integer count, AbstractCondition condition) {
        this.entityName = entityName;
        this.count = count;
        this.condition = condition;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getCount() {
        return count;
    }

}
