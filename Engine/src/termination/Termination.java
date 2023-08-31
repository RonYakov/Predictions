package termination;

import java.io.Serializable;

public class Termination implements Serializable {
    private final Integer ticks;
    private final Integer seconds;

    private Boolean byUser;

    public Termination(Integer ticks, Integer seconds, Boolean byUser) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.byUser = byUser;
    }

    public Boolean getByUser() {
        return byUser;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }
}
