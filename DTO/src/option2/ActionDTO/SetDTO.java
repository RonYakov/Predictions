package option2.ActionDTO;

public class SetDTO extends ActionDTO {
     private String value;
     private String property;

    public SetDTO(String name, String mainEntityName, String secondaryEntityName, String value, String property) {
        super(name, mainEntityName, secondaryEntityName);
        this.value = value;
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }
}
