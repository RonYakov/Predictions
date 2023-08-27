package simulation.api;

import property.instance.AbstractPropertyInstance;

import java.util.Map;

public interface EnvironmentsSimulation {
    public AbstractPropertyInstance getEnvironment(String environmentName);
}
