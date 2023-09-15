package ex2DTO;

public class EntityCountReqDTO {
    private String EntityName;
    private Integer id;

    public EntityCountReqDTO(String entityName, Integer id) {
        EntityName = entityName;
        this.id = id;
    }

    public String getEntityName() {
        return EntityName;
    }

    public Integer getId() {
        return id;
    }
}
