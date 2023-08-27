package option4;

public class SimulationDesiredInfoDTO {
    private Integer idNum;
    private InfoType type;

    public SimulationDesiredInfoDTO(Integer idNum, InfoType type) {
        this.idNum = idNum;
        this.type = type;
    }

    public Integer getIdNum() {
        return idNum;
    }

    public InfoType getType() {
        return type;
    }
}
