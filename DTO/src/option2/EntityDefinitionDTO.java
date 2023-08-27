package option2;

import jdk.internal.dynalink.linker.LinkerServices;

import java.util.List;

public class EntityDefinitionDTO {
    private String name;
    private Integer populationNumber;
    private List<PropertyDefinitionDTO> properties;

    public EntityDefinitionDTO(String name, Integer populationNumber, List<PropertyDefinitionDTO> properties) {
        this.name = name;
        this.populationNumber = populationNumber;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulationNumber() {
        return populationNumber;
    }

    public List<PropertyDefinitionDTO> getProperties() {
        return properties;
    }
}
