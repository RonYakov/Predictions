package option4.histogram;

import java.util.List;

public class HistogramAllEntitiesDTO {
    private List<String> names;

    public HistogramAllEntitiesDTO(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }
}
