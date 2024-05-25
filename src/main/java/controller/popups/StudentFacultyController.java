package controller.popups;

import app.SessionManager;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentFacultyController {

    public TextField getTxtFaculty() {
        return txtFaculty;
    }

        UserTableViewController utvc = new UserTableViewController();
    @FXML
    private TextField txtFaculty;
    public void handleSave(ActionEvent actionEvent) {
        System.out.println("Step 1");
        User selectedUser = SessionManager.getUser();
        int faculty_id = Integer.parseInt(txtFaculty.getText());

        String queryUpdateUser = "UPDATE users SET faculty_id = ? WHERE id = ?";
        System.out.println("Step 2");
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement updateUserStatement = connection.prepareStatement(queryUpdateUser)) {

            updateUserStatement.setInt(1, faculty_id);
            updateUserStatement.setInt(2, selectedUser.getId());
            updateUserStatement.executeUpdate();

            // Close the pop-up window
            Stage stage = (Stage) txtFaculty.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("Step 3");
        utvc.fetchDataFromDatabase();
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
