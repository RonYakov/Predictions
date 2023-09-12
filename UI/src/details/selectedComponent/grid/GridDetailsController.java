package details.selectedComponent.grid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GridDetailsController {

    @FXML
    private Label cols;

    @FXML
    private Label rows;

    public void setData(String rows, String cols) {
        this.cols.setText(cols);
        this.rows.setText(rows);
    }
}