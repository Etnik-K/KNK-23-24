package model.dto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Orari;
import model.User;
import service.DBConnector;

import java.sql.*;

public class OrariRecordDto extends Orari {
    @FXML
    private javafx.scene.control.TableView<Orari> TableView;
    public OrariRecordDto(int id, int fakulteti_id, int profesori_id, int lenda_id, int salla_id, int time_slot_id, Time start_time, Time end_time, String day_of_week, int capacity) {
        super(id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity);
    }


    // In the ProfessorController class
    @FXML
    private void handleView(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari");

            TableView.getItems().clear(); // Clear existing items before adding new ones

            while (rs.next()) {
                // Create an instance of the OrariRecordDto class and add it to the TableView
                TableView.getItems().add(new OrariRecordDto(
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
                ));
            }

            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
