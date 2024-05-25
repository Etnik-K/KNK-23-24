package database;

//import com.example.knk2324.java_05.ConnectionUtil;

import model.dto.CreateUserDto;
import model.dto.UserDto;
import repository.UserRepository;
import service.UserService;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/knk2024";
        String user = "root";
        String password = "Gresa.123";
        Connection connection = DriverManager.getConnection(
                url, user, password
        );

        if(connection.isValid(1000)){
            System.out.println("Lidhja me baze te te dhenave u krijuar me sukses!");
        }

       // String sql = "SELECT * FROM users";
        //Statement statement = connection.createStatement();
        //ResultSet result = statement.executeQuery(sql);


//        System.out.println(UserService.signUp(new UserDto(
//                "Etnik", "Kelmendi", "etnikkelmendi20@gmail.com", "123", "123")));
//        //System.out.println(UserRepository.create(new CreateUserDto("Etnik", "Kelmendi", "etnikkelmendi20@gmail.com", "111", "111")));


        //Leximi me Kod:
        /*while(result.next()){
            int id = result.getInt("id");
//            String firstName = result.getString("first_name");
//            String lastName = result.getString("last_name");
            String firstName = result.getString("firstName");
            System.out.println("Id: " + id);
            System.out.println("firstName: " + firstName);
//            System.out.println("First name: " + firstName);
//            System.out.println("Last name: " + lastName);

        }*/

        //Leximi me ane te metodes
        //lexoPerdoruesin("1; 0 = 0;");
    }

    public static void lexoPerdoruesin(String id) throws SQLException{
        String sql = "SELECT * FROM users WHERE id = ?";
       // System.out.println(sql);
        Connection connection = DatabaseUtil.getConnection();
//        Statement statement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement(
                sql
        );
        statement.setString(1, id);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            User user = User.getInstanceFromResultSet(result);
            user.printoDetajet();
        }
    }
}

class User {
    private int id;
    private String firstName;

    private User(int id, String firstName){
        this.id = id;
        this.firstName = firstName;
    }

    //E merer Userin prej databazes, mujna me i shtu senet qe i merr prej databazes, jo vec
    public static User getInstanceFromResultSet(ResultSet resultSet){
        try{
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            return new User(id, firstName);
        }catch (Exception e){
            return null;
        }
    }

    public void printoDetajet(){
        System.out.println("Id: " + this.id);
        System.out.println("firstName: " + this.firstName);
    }
}