package property.definition.value;

import java.io.Serializable;

public class PropertyDefinitionValue implements Serializable {
    private final boolean randomInitialize;
    private final String init;

    public PropertyDefinitionValue(boolean randomInitialize, String init) {
        this.randomInitialize = randomInitialize;
        this.init = init;
    }

    public boolean isRandomInitialize() {
        return randomInitialize;
    }
    public String getInit() {
        return init;
    }
}
