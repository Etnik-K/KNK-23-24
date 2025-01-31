package controller.tableView;

import app.Navigator;
import app.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import model.dto.OrariRecordDto;
import repository.UserRepository;
import service.DBConnector;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
//ndryshini i pare
//i dyti etje etj
public class OrariTableViewController implements Initializable {

    @FXML
    private TableView<OrariRecordDto> TableView;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colId;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colFakultetiId;

    @FXML
    private TableColumn<OrariRecordDto, String> colFacultyName;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colProfesoriId;

    @FXML
    private TableColumn<OrariRecordDto, String> colProfessorFirstName;

    @FXML
    private TableColumn<OrariRecordDto, String> colProfessorLastName;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colLendaId;

    @FXML
    private TableColumn<OrariRecordDto, String> colLendaName;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colSallaId;

    @FXML
    private TableColumn<OrariRecordDto, String> colSallaName;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colTimeSlotId;

    @FXML
    private TableColumn<OrariRecordDto, String> colDayOfWeek;

    @FXML
    private TableColumn<OrariRecordDto, String> colStartTime;

    @FXML
    private TableColumn<OrariRecordDto, String> colEndTime;

    @FXML
    private TableColumn<OrariRecordDto, Integer> colCapacity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link TableColumn with OrariRecordDto properties
        colId.setCellValueFactory(new PropertyValueFactory<>("orari_id"));
        colFakultetiId.setCellValueFactory(new PropertyValueFactory<>("fakulteti_id"));
        //colFacultyName.setCellValueFactory(new PropertyValueFactory<>("faculty_name"));
        colProfesoriId.setCellValueFactory(new PropertyValueFactory<>("profesori_id"));
        // colProfessorFirstName.setCellValueFactory(new PropertyValueFactory<>("professor_firstName"));
        //colProfessorLastName.setCellValueFactory(new PropertyValueFactory<>("professor_lastName"));
        colLendaId.setCellValueFactory(new PropertyValueFactory<>("lenda_id"));
//        colLendaName.setCellValueFactory(new PropertyValueFactory<>("lenda_name"));
        colSallaId.setCellValueFactory(new PropertyValueFactory<>("salla_id"));
        // colSallaName.setCellValueFactory(new PropertyValueFactory<>("salla_name"));
        colTimeSlotId.setCellValueFactory(new PropertyValueFactory<>("time_slot_id"));
        colDayOfWeek.setCellValueFactory(new PropertyValueFactory<>("day_of_week"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("end_time"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Fetch data from the database
        try {
            fetchDataFromDatabase(Navigator.ALL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    User currentUser = SessionManager.getUser();
    public void fetchDataFromDatabase(String day) {
        try {
            String query;
            if (currentUser.getUserType().equals("admin")){
                System.out.println("Te admini jena");
               /* Navigator.ALL = "";
                Navigator.MONDAY = "WHERE day_of_week='Monday'";
                Navigator.TUESDAY = "WHERE day_of_week='Tuesday'";
                Navigator.WEDNESDAY = "WHERE day_of_week='Wednesday'";
                Navigator.THURSDAY = "WHERE day_of_week='Thursday'";
                Navigator.FRIDAY = "WHERE day_of_week='Friday'";*/
                query = "SELECT * FROM orari_details " + day;
//                query = "SELECT ";
            }else{
                Navigator.ALL = "Where fakulteti_id = '";
                Navigator.MONDAY = "WHERE day_of_week = 'Monday' and fakulteti_id= '";
                Navigator.TUESDAY = "WHERE day_of_week='Tuesday' and fakulteti_id= '";
                Navigator.WEDNESDAY =  "WHERE day_of_week='Wednesday' and fakulteti_id= '";
                Navigator.THURSDAY = "WHERE day_of_week='Thursday' and fakulteti_id= '";
                Navigator.FRIDAY = "WHERE day_of_week='Friday' and fakulteti_id= '";
                query = "SELECT * FROM orari_details " + day + (UserRepository.loadUserFacultyId()) + "'";
            }
            Connection conn = DBConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            TableView.getItems().clear(); // Clear existing items before adding new ones

            while (rs.next()) {
                OrariRecordDto recordDto = new OrariRecordDto(
                        rs.getInt("orari_id"),
                        rs.getInt("fakulteti_id"),
                        rs.getInt("profesori_id"),
                        rs.getInt("lenda_id"),
                        rs.getInt("salla_id"),
                        rs.getInt("time_slot_id"),
                        rs.getString("day_of_week"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getInt("capacity")
                );
                TableView.getItems().add(recordDto);
            }

            rs.close();
            stmt.close();
            DBConnector.closeConnection(conn);
        } catch (SQLException e) {
           // e.printStackTrace();
        }
    }
}
