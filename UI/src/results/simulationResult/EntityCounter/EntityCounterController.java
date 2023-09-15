package results.simulationResult.EntityCounter;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class EntityCounterController {

    @FXML
    private LineChart<Integer, Integer> EntityCounterChart;

    public void addToChart(List<Integer> counter) {
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        int i = 0;
        for(Integer count : counter) {
            series.getData().add(new XYChart.Data<>(i * 5000, count));
            i++;
        }
        series.setName("Population amount");
        EntityCounterChart.getData().add(series);
    }
}

//    public void selectedItem() {
//        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
//
//        if (item != null && item.isLeaf()) {
//            String selectedProperty = item.getValue();
//
//            DTOPropertyHistogram histogramData = engineManager.getHistogram(selectedProperty);
//            barChart.getData().clear();
//            barChart.setAnimated(true);
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//
//            for (Map.Entry<String, Integer> entry : histogramData.getValues().entrySet()) {
//                String propertyValue = entry.getKey();
//                int frequency = entry.getValue();
//                series.getData().add(new XYChart.Data<>(propertyValue, frequency));
//            }
//
//            barChart.getData().add(series);
//            barChart.setAnimated(false);
//        }