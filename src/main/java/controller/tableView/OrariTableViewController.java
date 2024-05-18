package controller.tableView;

import app.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Orari;
import model.dto.OrariRecordDto;
import service.DBConnector;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrariTableViewController implements Initializable {

    @FXML
    private TableView<Orari> TableView;

    @FXML
    private TableColumn<Orari, Integer> colId;

    @FXML
    private TableColumn<Orari, Integer> colFakultetiId;

    @FXML
    private TableColumn<Orari, Integer> colProfesoriId;

    @FXML
    private TableColumn<Orari, Integer> colLendaId;

    @FXML
    private TableColumn<Orari, Integer> colSallaId;

    @FXML
    private TableColumn<Orari, Integer> colTimeSlotId;

    @FXML
    private TableColumn<Orari, String> colStartTime;

    @FXML
    private TableColumn<Orari, String> colEndTime;

    @FXML
    private TableColumn<Orari, String> colDayOfWeek;

    @FXML
    private TableColumn<Orari, Integer> colCapacity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link TableColumn with Orari properties
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFakultetiId.setCellValueFactory(new PropertyValueFactory<>("fakultetiId"));
        colProfesoriId.setCellValueFactory(new PropertyValueFactory<>("profesoriId"));
        colLendaId.setCellValueFactory(new PropertyValueFactory<>("lendaId"));
        colSallaId.setCellValueFactory(new PropertyValueFactory<>("sallaId"));
        colTimeSlotId.setCellValueFactory(new PropertyValueFactory<>("timeSlotId"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colDayOfWeek.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Fetch data from the database
        fetchDataFromDatabase(Navigator.ALL);
    }

    public void fetchDataFromDatabase(String day) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari " + day);

            TableView.getItems().clear(); // Clear existing items before adding new ones

            while (rs.next()) {
                OrariRecordDto recordDto = new OrariRecordDto(
                        rs.getInt("id"),
                        rs.getInt("fakulteti_id"),
                        rs.getInt("profesori_id"),
                        rs.getInt("lenda_id"),
                        rs.getInt("salla_id"),
                        rs.getInt("time_slot_id"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("day_of_week"),
                        rs.getInt("capacity")
                );

                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

}
