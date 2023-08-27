package property.definition.range;

import java.io.Serializable;

public class Range implements Serializable {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public Boolean IsInRange(double value) {
        if (value >= from && value <= to) {
            return true;
        } else {
            return false;
        }
    }
}
