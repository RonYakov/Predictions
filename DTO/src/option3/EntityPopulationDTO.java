package option3;

public class EntityPopulationDTO {
    private String entityName;
    private Integer count;

    public EntityPopulationDTO(String entityName, Integer count) {
        this.entityName = entityName;
        this.count = count;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getCount() {
        return count;
    }
}
