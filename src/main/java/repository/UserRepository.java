package repository;

import app.Navigator;
import app.SessionManager;
import model.User;
import model.dto.CreateUserDto;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static boolean create(CreateUserDto userData) throws SQLException {
        Connection conn = DBConnector.getConnection();
        String query = """
                INSERT INTO users (firstName, lastName, email, salt, passwordHash, user_type)
                VALUE (?, ?, ?, ?, ?, ?)
                """;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userData.getFirstName());
            pst.setString(2, userData.getLastName());
            pst.setString(3, userData.getEmail());
            pst.setString(4, userData.getSalt());
            pst.setString(5, userData.getPasswordHash());
            pst.setString(6, userData.getSelectedRole());
            pst.execute();
            pst.close();
            System.out.println("Query executed successfully");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public static User getByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? LIMIT 1";
        Connection connection = DBConnector.getConnection();

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();

            if (result.next()) {
                return getFromResultSet(result);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static User getFromResultSet(ResultSet result) {
        try {
            int id = result.getInt("id");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            String userType = result.getString("user_type");
            boolean approved = result.getBoolean("is_approved"); // Retrieve is_approved from the ResultSet
            return new User(id, firstName, lastName, email, salt, passwordHash, userType, approved);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int loadUserFacultyId() {
        User currentUser = SessionManager.getUser();
        String query;
        if (currentUser == null) {
            return 0;
        }

        int userId = currentUser.getId();
        try (Connection conn = DBConnector.getConnection()) {
            if(currentUser.getUserType().equals("student")){
            query = "SELECT faculty_id FROM users WHERE id = ?";
            }
            else{
                query = "SELECT faculty_id FROM profesor WHERE id = ?";
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int facultyId = rs.getInt("faculty_id");
                        Navigator.Faculty_id = facultyId;
                        SessionManager.setUser(currentUser); // Update user in session with facultyId
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Navigator.Faculty_id;
    }
}
