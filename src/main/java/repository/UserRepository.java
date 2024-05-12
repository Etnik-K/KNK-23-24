package repository;

import model.User;
import model.dto.CreateUserDto;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLOutput;

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
//            conn.close();
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
            System.out.println("ESHTE NTRY");

            if(result.next()){
                System.out.println("ESHTE N'IF");
                return getFromResultSet(result);

            }
            return null;
        }catch (Exception e){
            System.out.println("ERRORRRRR------------");
            e.printStackTrace();
            return null;
        }
    }


    private static User getFromResultSet(ResultSet result){
        try{
            int id = result.getInt("id");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            String selectedRole = result.getString("user_type");
            return new User(
                    id, firstName, lastName, email, salt, passwordHash, selectedRole
            );
        }catch (Exception e){
            System.out.println("-------------------");
            return null;
        }
    }







}
