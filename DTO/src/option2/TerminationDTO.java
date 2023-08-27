package option2;

public class TerminationDTO {
    private Integer ticks;
    private Integer seconds;

    public TerminationDTO(Integer ticks, Integer seconds) {
        this.ticks = ticks;
        this.seconds = seconds;
    }

    //todo - need to remember in the UI that they could be a null!!
    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }
}
