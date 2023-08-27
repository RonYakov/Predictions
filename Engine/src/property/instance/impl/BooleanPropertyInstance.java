package property.instance.impl;

import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;

import static utills.string.StringConvertor.convertStringToBool;

public class BooleanPropertyInstance extends AbstractPropertyInstance {
    private Boolean value;

    public BooleanPropertyInstance(Boolean value , String name) {
        super(name);
        this.value = value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.BOOLEAN;
    }

    @Override
    public String getValue() {
        return Boolean.toString(value);
    }

    @Override
    public void setValue(String value) {
        try {
            this.value = convertStringToBool(value);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage() + "Error occurred in setValue in BooleanPropertyInstance class");
        }
    }
}
