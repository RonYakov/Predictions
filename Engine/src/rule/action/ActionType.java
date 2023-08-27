package rule.action;

import java.io.Serializable;

public enum ActionType implements Serializable {
    INCREASE, DECREASE, MULTIPLY, DIVIDE, SINGLE_CONDITION, MULTIPLE_CONDITION, SET, KILL
}
