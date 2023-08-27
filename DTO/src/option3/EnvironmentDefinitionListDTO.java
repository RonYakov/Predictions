package option3;

import java.util.List;

public class EnvironmentDefinitionListDTO {
    List<EnvironmentDefinitionDTO> environmentDefinitionDTOList;

    public EnvironmentDefinitionListDTO(List<EnvironmentDefinitionDTO> environmentDefinitionDTOList) {
        this.environmentDefinitionDTOList = environmentDefinitionDTOList;
    }

    public List<EnvironmentDefinitionDTO> getEnvironmentDefinitionDTOList() {
        return environmentDefinitionDTOList;
    }
}
