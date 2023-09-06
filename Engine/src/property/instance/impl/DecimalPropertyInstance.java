package property.instance.impl;
import property.definition.PropertyType;
import property.definition.range.Range;
import property.instance.AbstractPropertyInstance;

import static utills.string.StringConvertor.convertStringToFloat;
import static utills.string.StringConvertor.convertStringToInt;

public class DecimalPropertyInstance extends AbstractPropertyInstance {
    private Integer value;

    public DecimalPropertyInstance(String name, Range range, Integer value){
        super(name,range, PropertyType.DECIMAL);
        this.value = value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.DECIMAL;
    }

    @Override
    public String getValue() {
        return Integer.toString(value);
    }
    @Override
    public void setValue(String value) {
        Integer newValue;

        try {
            newValue = convertStringToInt(value);
            setTicks(0);
            setModified(true);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage() + "Error occurred in setValue in DecimalPropertyInstance class");
        }

        if(!isInRange(newValue)) {
            if(newValue > getRange().getTo()) {
                newValue = (int)getRange().getTo();
            }
            else {
                newValue = (int)getRange().getTo();
            }
        }

        this.value = newValue;
    }
}
