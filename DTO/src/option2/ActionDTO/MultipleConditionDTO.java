package option2.ActionDTO;

public class MultipleConditionDTO extends ActionDTO {
    private final String logic;
    private final Integer thisActionAmount;
    private final Integer elseActionAmount;

    public MultipleConditionDTO(String name, String mainEntityName, String secondaryEntityName, String logic, Integer thisActionAmount, Integer elseActionAmount) {
        super(name, mainEntityName, secondaryEntityName);
        this.logic = logic;
        this.thisActionAmount = thisActionAmount;
        this.elseActionAmount = elseActionAmount;
    }

    public String getLogic() {
        return logic;
    }

    public Integer getThisActionAmount() {
        return thisActionAmount;
    }

    public Integer getElseActionAmount() {
        return elseActionAmount;
    }
}
