package service;

import app.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.dto.CreateUserDto;
import model.dto.LoginUserDto;
import model.dto.UserDto;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class UserService {
    public static boolean signUp(UserDto userData) throws SQLException {
        String password = userData.getPassword();
        String confirmPassword = userData.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            return false;
        }


        String salt = PasswordHasher.generateSalt();
        String passwordHash = PasswordHasher.generateSaltedHash(password, salt);

        CreateUserDto createUserData = new CreateUserDto(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                salt,
                passwordHash,
                userData.getSelectedRole()
        );

        if (UserRepository.create(createUserData)) {
            SessionManager.setUser(UserRepository.getByEmail(userData.getEmail()));
            return true;
        }
        // ska nevoj per else
        return false;
    }


    public static boolean login(LoginUserDto loginData) throws SQLException {
        User user = UserRepository.getByEmail(loginData.getEmail());
        if (user == null) {
            return false;
        }

        // Check if the user is approved
        if (!user.isApproved()) {
            try {
                // Load the Denied.fxml file
                FXMLLoader loader = new FXMLLoader(UserService.class.getResource("/app/denied.fxml"));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Access Denied");

                //
//                Nuk t'len me prek kurgjo mrena faqes login deri sa ta mshel ket popupfile
                stage.initModality(Modality.APPLICATION_MODAL);


                stage.showAndWait(); // Show and wait until the new stage is closed
            } catch (IOException e) {
                e.printStackTrace(); // Handle error loading FXML file
            }

            return false;
        }

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getPasswordHash();

        if (PasswordHasher.compareSaltedHash(password, salt, passwordHash)) {
            SessionManager.setUser(user);
            return true;
        }
        // Invalid password, return false
        return false;
    }

}
