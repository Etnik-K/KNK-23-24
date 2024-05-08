package repository;

import database.DatabaseUtil;
import model.User;
import model.dto.CreateUserDto;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static boolean create(CreateUserDto userData){
        Connection conn = DBConnector.getConnection();
        String query = """
                INSERT INTO users (firstName, lastName, email, salt, passwordHash, user_type)
                VALUE (?, ?, ?, ?, ?, ?)
                """;
        //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
        try{
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userData.getFirstName());
            pst.setString(2, userData.getLastName());
            pst.setString(3, userData.getEmail());
            pst.setString(4, userData.getSalt());
            pst.setString(5, userData.getPasswordHash());
            pst.setString(6, userData.getSelectedRole());
            pst.execute();
            pst.close();
            conn.close();
            System.out.println("U ekzekutu query me sukses");
            return true;
        }catch (Exception e){
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }



    public static User getByEmail(String email){
        String query = "SELECT * FROM users WHERE email = ? LIMIT 1";
        Connection connection = DBConnector.getConnection();

        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                return getFromResultSet(result);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }



//    public static boolean emailExists(String email){
//        String q = "SELECT * FROM users WHERE email = ? LIMIT 1";
//        Connection connection = DBConnector.getConnection();
//        boolean exists = false;
//        ResultSet result = null;
//        PreparedStatement pst=null;
//        try{
//            pst = connection.prepareStatement(q);
//            pst.setString(1, email);
//            result = pst.executeQuery();
//            if(result.next()){
//                int count = result.getInt(1);
//                exists = count>0;
//            }
//        }catch(SQLException sqle){
//            sqle.printStackTrace();
//        }
//        finally {
//            try {
//                if (result != null) result.close();
//                if (pst != null) pst.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return exists;
//    }
//public static boolean emailExists(String email){
//    String query = "SELECT COUNT(*) FROM users WHERE email = ?";
//    try(Connection connection = DBConnector.getConnection();
//        PreparedStatement pst = connection.prepareStatement(query)) {
//        pst.setString(1, email);
//        ResultSet result = pst.executeQuery();
//        if(result.next()){
//            int count = result.getInt(1);
//            return count > 0;
//        }
//    }catch (SQLException e){
//        e.printStackTrace();
//    }
//    return false;
//}

    private static User getFromResultSet(ResultSet result){
        try{
            int id = result.getInt("id");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            String selectedRole = result.getString("selectedRole");
            return new User(
                    id, firstName, lastName, email, salt, passwordHash, selectedRole
            );
        }catch (Exception e){
            return null;
        }
    }







}
