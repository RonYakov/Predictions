package option3;

public class SimulationFinishDTO {
    private Integer simulationID;
    private String simulationStopCause;

    public SimulationFinishDTO(Integer simulationID, String simulationStopCause) {
        this.simulationID = simulationID;
        this.simulationStopCause = simulationStopCause;
    }

    public Integer getSimulationID() {
        return simulationID;
    }

    public String getSimulationStopCause() {
        return simulationStopCause;
    }
}
