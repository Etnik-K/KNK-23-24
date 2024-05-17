package repository;

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
}
