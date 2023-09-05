package option2;

import java.util.List;

public class SimulationDefinitionDTO {
    private List<EntityDefinitionDTO> entityDefinitionDTOList;
    private List<RulesDTO> rulesDTOList;
    private TerminationDTO terminationDTO;
    private int gridRows;
    private int gridCols;

    public SimulationDefinitionDTO(List<EntityDefinitionDTO> entityDefinitionDTOList, List<RulesDTO> rulesDTOList, TerminationDTO terminationDTO, int gridRows, int gridCols) {
        this.entityDefinitionDTOList = entityDefinitionDTOList;
        this.rulesDTOList = rulesDTOList;
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

    public List<RulesDTO> getRulesDTOList() {
        return rulesDTOList;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
