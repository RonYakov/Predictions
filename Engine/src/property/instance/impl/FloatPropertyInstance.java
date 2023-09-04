package property.instance.impl;

import property.definition.PropertyType;
import property.definition.range.Range;
import property.instance.AbstractPropertyInstance;

import static utills.string.StringConvertor.convertStringToFloat;

public class FloatPropertyInstance extends AbstractPropertyInstance {
    private  Float value;

    public FloatPropertyInstance(String name, Range range, Float value){
        super(name,range);
        this.value = value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.FLOAT;
    }

    @Override
    public String getValue() {
        return Float.toString(value);
    }
    @Override
    public void setValue(String value) {
        Float newValue;

        try {
            newValue = convertStringToFloat(value);
            setTicks(0);
            setModified(true);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage() + "Error occurred in setValue in FloatPropertyInstance class");
        }

        if(!isInRange(newValue)) {
            if(newValue > getRange().getTo()) {
                newValue = (float)getRange().getTo();
            }
            else {
                newValue = (float)getRange().getTo();
            }
        }

        this.value = newValue;
    }
}
