package option2;

import java.util.List;

public class SimulationDefinitionDTO {
    private List<EntityDefinitionDTO> entityDefinitionDTOList;
    private List<RulesDTO> rulesDTOList;
    private List<PropertyDefinitionDTO> environmentDefenitionDTOList;
    private TerminationDTO terminationDTO;
    private int gridRows;
    private int gridCols;

    public SimulationDefinitionDTO(List<EntityDefinitionDTO> entityDefinitionDTOList, List<RulesDTO> rulesDTOList, List<PropertyDefinitionDTO> environmentDefenitionDTOList, TerminationDTO terminationDTO, int gridRows, int gridCols) {
        this.entityDefinitionDTOList = entityDefinitionDTOList;
        this.rulesDTOList = rulesDTOList;
        this.environmentDefenitionDTOList = environmentDefenitionDTOList;
        this.terminationDTO = terminationDTO;
        this.gridRows = gridRows;
        this.gridCols = gridCols;
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getGridCols() {
        return gridCols;
    }

    public List<EntityDefinitionDTO> getEntityDefinitionDTOList() {
        return entityDefinitionDTOList;
    }

    public List<PropertyDefinitionDTO> getEnvironmentDefenitionDTOList() {
        return environmentDefenitionDTOList;
    }

    public List<RulesDTO> getRulesDTOList() {
        return rulesDTOList;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
