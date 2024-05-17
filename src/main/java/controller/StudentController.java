package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Orari;
import model.dto.OrariRecordDto;
import service.DBConnector;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private Button btnview;

    @FXML
    private Button btnMonday;

    @FXML
    private Button btnTuesday;

    @FXML
    private Button btnWednesday;

    @FXML
    private Button btnThursday;

    @FXML
    private Button btnFriday;
    @FXML
    private javafx.scene.control.TableView<Orari> TableView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        // Set text for labels, buttons, text elements, etc.
        // btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }

    @FXML
    private VBox resultContainer;

    @FXML
    private void handleLogOut(ActionEvent ae){

    }
    @FXML
    private void handleMonday(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari WHERE day_of_week='Monday'");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTuesday(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari WHERE day_of_week='Tuesday'");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleWednesday(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari WHERE day_of_week='Wednesday'");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleThursday(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari WHERE day_of_week='Thursday'");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFriday(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari WHERE day_of_week='Friday'");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSearch(ActionEvent ae){

    }
    @FXML
    private void handleView(ActionEvent ae) {
        try {
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, fakulteti_id, profesori_id, lenda_id, salla_id, time_slot_id, start_time, end_time, day_of_week, capacity FROM Orari");

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
                System.out.println("Adding record: " + recordDto.toString()); // Add this line for debugging
                TableView.getItems().add(recordDto);
            }


            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        Locale newLocale;
        if (Locale.getDefault().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        // Change the language
        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);

        // Update the text of all elements
        updateText(newLocale);
        System.out.println("Gjuha: " + newLocale.getLanguage());
    }
    private void updateText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        //btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }
}
