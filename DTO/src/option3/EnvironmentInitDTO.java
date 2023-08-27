package option3;

public class EnvironmentInitDTO {
    private String name;
    private String newValue;

    public EnvironmentInitDTO(String name, String newValue) {
        this.name = name;
        this.newValue = newValue;
    }

    public String getName() {
        return name;
    }

    public String getNewValue() {
        return newValue;
    }
}
