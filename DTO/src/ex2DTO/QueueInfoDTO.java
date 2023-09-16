package ex2DTO;

public class QueueInfoDTO {
    private Integer simRunning;
    private Integer simInQueue;
    private Integer simDone;

    public QueueInfoDTO(Integer simRunning, Integer simInQueue, Integer simDone) {
        this.simRunning = simRunning;
        this.simInQueue = simInQueue;
        this.simDone = simDone;
    }

    public Integer getSimRunning() {
        return simRunning;
    }

    public Integer getSimInQueue() {
        return simInQueue;
    }

    public Integer getSimDone() {
        return simDone;
    }
}
