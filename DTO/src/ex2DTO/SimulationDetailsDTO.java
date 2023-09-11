package ex2DTO;

import option2.TerminationDTO;

import java.util.List;

public class SimulationDetailsDTO {
    List<EntityCountDTO> entityCountDTOList;
    TerminationDTO terminationDTO;

    String simulationState;

    public SimulationDetailsDTO(List<EntityCountDTO> entityCountDTOList, TerminationDTO terminationDTO, String simulationState) {
        this.entityCountDTOList = entityCountDTOList;
        this.terminationDTO = terminationDTO;
        this.simulationState = simulationState;
    }

    public String getSimulationState() {
        return simulationState;
    }

    public List<EntityCountDTO> getEntityCountDTOList() {
        return entityCountDTOList;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
