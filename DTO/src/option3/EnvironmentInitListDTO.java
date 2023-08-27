package option3;

import java.util.List;

public class EnvironmentInitListDTO {
    List<EnvironmentInitDTO> environmentInitListDTOList;

    public EnvironmentInitListDTO(List<EnvironmentInitDTO> environmentInitListDTOList) {
        this.environmentInitListDTOList = environmentInitListDTOList;
    }

    public List<EnvironmentInitDTO> getEnvironmentInitListDTOList() {
        return environmentInitListDTOList;
    }
}
