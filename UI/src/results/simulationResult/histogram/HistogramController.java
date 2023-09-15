package results.simulationResult.histogram;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistogramController {

    @FXML
    private TableColumn<HistogramCell, String> Amount;
    @FXML
    private TableView<HistogramCell> histogram;
    @FXML
    private TableColumn<HistogramCell, String> propertyVal;

    private ObservableList<HistogramCell> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        propertyVal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPropertyVal()));
        Amount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAmount()));

        histogram.setItems(data);
    }

    public void addDataToTable(String propertyValue, String amount) {
        HistogramCell newData = new HistogramCell(propertyValue, amount);
        data.add(newData);
    }
}
