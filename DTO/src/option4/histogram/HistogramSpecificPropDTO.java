package option4.histogram;

import java.util.Map;

public class HistogramSpecificPropDTO {
    private String name;

    private Map<String, Integer> histogram;

    public HistogramSpecificPropDTO(String name, Map<String, Integer> histogram) {
        this.name = name;
        this.histogram = histogram;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getHistogram() {
        return histogram;
    }
}
