package option2;

public class PropertyDefinitionDTO {
    private String name;
    private String type;
    private Double rangeFrom;
    private Double rangeTo;
    private Boolean isRandomize;

    public PropertyDefinitionDTO(String name, String type, Double rangeFrom, Double rangeTo, Boolean isRandomize) {
        this.name = name;
        this.type = type;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomize = isRandomize;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    //todo - need to remember in the UI that the from and to could be a null!!
    public Double getRangeFrom() {
        return rangeFrom;
    }

    public Double getRangeTo() {
        return rangeTo;
    }

    public Boolean getRandomize() {
        return isRandomize;
    }
}
