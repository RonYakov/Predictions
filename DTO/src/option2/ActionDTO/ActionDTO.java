package option2.ActionDTO;

public abstract class ActionDTO {
    private final String name;
    private final String mainEntityName;
    private final String secondaryEntityName;


    public ActionDTO(String name, String mainEntityName, String secondaryEntityName) {
        this.name = name;
        this.mainEntityName = mainEntityName;
        this.secondaryEntityName = secondaryEntityName;
    }

    public String getName() {
        return name;
    }

    public String getMainEntityName() {
        return mainEntityName;
    }
    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

}
