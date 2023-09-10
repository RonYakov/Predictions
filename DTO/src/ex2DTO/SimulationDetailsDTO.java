package ex2DTO;

import option2.TerminationDTO;

import java.util.List;

public class SimulationDetailsDTO {
    List<EntityCountDTO> entityCountDTOList;
    TerminationDTO terminationDTO;

    public SimulationDetailsDTO(List<EntityCountDTO> entityCountDTOList, TerminationDTO terminationDTO) {
        this.entityCountDTOList = entityCountDTOList;
        this.terminationDTO = terminationDTO;
    }

    public List<EntityCountDTO> getEntityCountDTOList() {
        return entityCountDTOList;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
