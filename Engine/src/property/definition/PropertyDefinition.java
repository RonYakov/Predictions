package property.definition;

import property.definition.range.Range;
import property.definition.value.PropertyDefinitionValue;

import java.io.Serializable;
import java.util.Objects;

public class PropertyDefinition implements Serializable {
    private final String name;
    private final PropertyType type;
    private final Range range;
    private final PropertyDefinitionValue value;
    protected static final int NO_RANGE_PROP = 0;


    public PropertyDefinition(String name, String type, PropertyDefinitionValue value , Range range) {
        this.name = name;
        switch (type) {
            case "decimal":
                this.type = PropertyType.DECIMAL;
                break;
            case "float":
                this.type = PropertyType.FLOAT;
                break;
            case "boolean":
                this.type = PropertyType.BOOLEAN;
                break;
            default:
                this.type = PropertyType.STRING;
                break;
        }
        this.value = value;
        this.range = range;
    }

    public PropertyType getType() {
        return type;
    }

    public Range getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    public PropertyDefinitionValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDefinition property = (PropertyDefinition) o;
        return Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
