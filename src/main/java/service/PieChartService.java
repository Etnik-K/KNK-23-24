package service;

import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class PieChartService {
    public static void retrievePieChartData(PieChart PieChartPerSalla){
        String query = "select s.salla_name as salla, count(o.id) as count from salla s join orari o on s.id = o.salla_id group by salla;";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                int salla = resultSet.getInt("salla");
                int count = resultSet.getInt("count");
                PieChartPerSalla.getData().add(new PieChart.Data(salla+"", count));
            }
        } catch (SQLException e){
            System.err.println("SQL Exception");
            System.err.println(e.getSQLState());
            System.err.println(e.getMessage());
        }

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
