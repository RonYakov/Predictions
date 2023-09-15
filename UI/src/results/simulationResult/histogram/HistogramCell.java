package results.simulationResult.histogram;

import javafx.beans.property.SimpleStringProperty;

public class HistogramCell {
    private final SimpleStringProperty propertyVal;
    private final SimpleStringProperty amount;

    public HistogramCell(String propertyVal, String amount) {
        this.propertyVal = new SimpleStringProperty(propertyVal);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getPropertyVal() {
        return propertyVal.get();
    }

    public String getAmount() {
        return amount.get();
    }
}
