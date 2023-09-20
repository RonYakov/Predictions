package ex2DTO;

import java.util.List;

public class EntityCountResDTO {
    private List<Integer> entityCountSamples;
    private Integer currTick;

    public EntityCountResDTO(List<Integer> entityCountSamples, Integer currTick) {
        this.entityCountSamples = entityCountSamples;
        this.currTick = currTick;
    }

    public List<Integer> getEntityCountSamples() {
        return entityCountSamples;
    }

    public Integer getCurrTick() {
        return currTick;
    }
}
