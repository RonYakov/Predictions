package property.instance.impl;

import property.definition.PropertyType;
import property.instance.AbstractPropertyInstance;

import java.util.Random;

public class StringPropertyInstance extends AbstractPropertyInstance {
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,_-(). ";
    private static final int MAX_STRING_LENGTH = 50;
    private String value;

    public StringPropertyInstance(String name, String value) {
        super(name , PropertyType.STRING);
        this.value = value;

    }

    @Override
    public PropertyType getType() {
        return PropertyType.STRING;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        addToUnchangedSum();
        setTicks(0);
        setModified(true);
    }
}
