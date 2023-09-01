package option3;

import java.util.List;

public class SimulationDataForStartDTO {
    private EnvironmentInitListDTO environmentInitListDTO;
    private List<EntityPopulationDTO> entityPopulationDTOList;

    public SimulationDataForStartDTO(EnvironmentInitListDTO environmentInitListDTO, List<EntityPopulationDTO> entityPopulationDTOList) {
        this.environmentInitListDTO = environmentInitListDTO;
        this.entityPopulationDTOList = entityPopulationDTOList;
    }

    public EnvironmentInitListDTO getEnvironmentInitListDTO() {
        return environmentInitListDTO;
    }

    public List<EntityPopulationDTO> getEntityPopulationDTOList() {
        return entityPopulationDTOList;
    }
}
