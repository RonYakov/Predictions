package ex2DTO;

import java.util.List;

public class EntityCountResDTO {
    private List<Integer> entityCountSamples;

    public EntityCountResDTO(List<Integer> entityCountSamples) {
        this.entityCountSamples = entityCountSamples;
    }

    public List<Integer> getEntityCountSamples() {
        return entityCountSamples;
    }
}
