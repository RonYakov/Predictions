package option4;

public class PastSimulationInfoDTO {
    private String date;
    private Integer idNum;

    public PastSimulationInfoDTO(String date, Integer idNum) {
        this.date = date;
        this.idNum = idNum;
    }

    public String getDate() {
        return date;
    }

    public Integer getIdNum() {
        return idNum;
    }
}
