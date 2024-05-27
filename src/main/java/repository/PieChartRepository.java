package repository;

import javafx.scene.chart.PieChart;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieChartRepository {
    public static void retrievePieChartData(PieChart PieChartPerSalla) {
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
    }
}
