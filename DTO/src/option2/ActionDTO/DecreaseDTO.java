package option2.ActionDTO;

public class DecreaseDTO extends ActionDTO {

    private String property;
    private String by;

    public DecreaseDTO(String name, String mainEntityName, String secondaryEntityName, String property, String by) {
        super(name, mainEntityName, secondaryEntityName);
        this.property = property;
        this.by = by;
    }

    public String getProperty() {
        return property;
    }

    public String getBy() {
        return by;
    }
}
