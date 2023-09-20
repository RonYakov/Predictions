package results.simulationResult.EntityCounter;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class EntityCounterController {

    @FXML
    private LineChart<Integer, Integer> EntityCounterChart;

    public void addToChart(List<Integer> counter, int currTick) {
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        for(int i =0 ; i < counter.size() -1; i++) {
            series.getData().add(new XYChart.Data<>(i * 5000, counter.get(i)));
        }
        series.getData().add(new XYChart.Data<>(currTick, counter.get(counter.size() - 1)));
        series.setName("Population amount");
        EntityCounterChart.getData().add(series);
    }
}