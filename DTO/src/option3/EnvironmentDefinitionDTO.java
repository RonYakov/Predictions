package option3;

public class EnvironmentDefinitionDTO {
    private String name;
    private String type;
    private String rangeFrom;
    private String rangeTo;

    public EnvironmentDefinitionDTO(String name, String type, String rangeFrom, String rangeTo) {
        this.name = name;
        this.type = type;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRangeFrom() {
        return rangeFrom;
    }

    public String getRangeTo() {
        return rangeTo;
    }
}
