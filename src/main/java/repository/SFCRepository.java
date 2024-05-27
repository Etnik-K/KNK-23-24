package repository;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SFCRepository {
    public static void handleSaveRepo(int faculty_id, User selectedUser, TextField txtFaculty) {
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
//            e.printStackTrace();
            System.err.println("SQL Exception...");
            System.err.println(e.getSQLState());
            System.err.println(e.getMessage());
        }
    }
}
