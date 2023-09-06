package property.instance;

import property.definition.PropertyType;
import property.definition.range.Range;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractPropertyInstance implements Serializable {
    private final String name;
    private final Range range;
    private final PropertyType type;
    private int ticks = 0;
    private boolean modified = false;

    public String getName() {
        return name;
    }

    public AbstractPropertyInstance(String name , PropertyType type) {
        this.name = name;
        this.range = null;
        this.type = type;
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

    public AbstractPropertyInstance(String name, Range range , PropertyType type) {
        this.name = name;
        this.range = range;
        this.type = type;
    }

    public Boolean isInRange(Number number) {
        if(range == null) {
            return true;
        }
        return range.IsInRange(number.doubleValue());
    }

    public abstract void setValue(String value);
    public abstract String getValue();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPropertyInstance that = (AbstractPropertyInstance) o;
        return Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
