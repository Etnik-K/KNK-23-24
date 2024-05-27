package controller.popups;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import service.DBConnector;
import service.PieChartService;

import java.net.URL;
import java.util.ResourceBundle;

public class PieChartPerSallaController implements Initializable {
    @FXML
    private PieChart PieChartPerSalla;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PieChartService.retrievePieChart(this.PieChartPerSalla);
    }
}
