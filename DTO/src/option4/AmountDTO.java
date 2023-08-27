package option4;

import java.util.List;

public class AmountDTO {
    private List<String> entityNames;
    private List<Integer> startingCount;
    private List<Integer> endingCount;

    public AmountDTO(List<String> entityNames, List<Integer> startingCount, List<Integer> endingCount) {
        this.entityNames = entityNames;
        this.startingCount = startingCount;
        this.endingCount = endingCount;
    }

    public List<String> getEntityNames() {
        return entityNames;
    }

    public List<Integer> getStartingCount() {
        return startingCount;
    }

    public List<Integer> getEndingCount() {
        return endingCount;
    }
}
