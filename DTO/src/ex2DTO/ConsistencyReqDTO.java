package ex2DTO;

public class ConsistencyReqDTO {
    private String entity;
    private String property;
    private Integer id;

    public ConsistencyReqDTO(String entity, String property, Integer id) {
        this.entity = entity;
        this.property = property;
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public String getProperty() {
        return property;
    }

    public Integer getId() {
        return id;
    }
}
