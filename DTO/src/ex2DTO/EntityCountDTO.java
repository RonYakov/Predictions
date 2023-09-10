package ex2DTO;

public class EntityCountDTO {
    private String Name;
    private Integer Count;

    public EntityCountDTO(String name, Integer count) {
        Name = name;
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public Integer getCount() {
        return Count;
    }
}
