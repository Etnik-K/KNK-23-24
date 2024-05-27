package service;

import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import repository.PieChartRepository;

import java.text.DecimalFormat;

public class PieChartService {
    public static void retrievePieChart(PieChart PieChartPerSalla){
        String query = "select s.salla_name as salla, count(o.id) as count from salla s join orari o on s.id = o.salla_id group by salla;";
        PieChartRepository.retrievePieChartData(PieChartPerSalla);
        double sum = PieChartPerSalla.getData().stream().mapToDouble(data -> data.pieValueProperty().getValue()).sum();
        PieChartPerSalla.getData().forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName(), ": ",  format(data.pieValueProperty().divide(sum).multiply(100).getValue()),  "% "));
        });
    }
    private static double format(Double d){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(d));
    }
}
