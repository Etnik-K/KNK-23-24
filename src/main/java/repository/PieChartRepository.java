package repository;

import model.dto.PieChartDto;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieChartRepository {

    private static final String query = "select s.salla_name as salla, count(o.id) as count from salla s join orari o on s.id = o.salla_id group by salla;";

    public static List<PieChartDto> retrievePieChartData() {
        List<PieChartDto> list = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                int salla = resultSet.getInt("salla");
                int count = resultSet.getInt("count");
                list.add(new PieChartDto(salla, count));
            }
        } catch (SQLException e){
            System.err.println("SQL Exception");
            System.err.println(e.getSQLState());
            System.err.println(e.getMessage());
        }

        return list;
    }
}
