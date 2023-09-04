package property.instance;

import property.definition.PropertyType;
import property.definition.range.Range;

import java.io.Serializable;

public abstract class AbstractPropertyInstance implements Serializable {
    private final String name;
    private final Range range;
    private int ticks = 0;
    private boolean modified = false;

    public String getName() {
        return name;
    }

    public AbstractPropertyInstance(String name) {
        this.name = name;
        this.range = null;
    }

    public Range getRange() {
        return range;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isModified() {
        return modified;
    }

    public abstract PropertyType getType();

    public AbstractPropertyInstance(String name, Range range) {
        this.name = name;
        this.range = range;
    }

    public Boolean isInRange(Number number) {
        if(range == null) {
            return true;
        }
        return range.IsInRange(number.doubleValue());
    }

    public abstract void setValue(String value);
    public abstract String getValue();
}
