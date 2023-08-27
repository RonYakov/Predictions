package option2;

import java.util.List;

public class SimulationDefinitionDTO {
    private List<EntityDefinitionDTO> entityDefinitionDTOList;
    private List<RulesDTO> rulesDTOList;
    private TerminationDTO terminationDTO;

    public SimulationDefinitionDTO(List<EntityDefinitionDTO> entityDefinitionDTOList, List<RulesDTO> rulesDTOList, TerminationDTO terminationDTO) {
        this.entityDefinitionDTOList = entityDefinitionDTOList;
        this.rulesDTOList = rulesDTOList;
        this.terminationDTO = terminationDTO;
    }

    public List<EntityDefinitionDTO> getEntityDefinitionDTOList() {
        return entityDefinitionDTOList;
    }

    public List<RulesDTO> getRulesDTOList() {
        return rulesDTOList;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
