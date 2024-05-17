package service;

import app.SessionManager;
import model.User;
import model.dto.CreateUserDto;
import model.dto.LoginUserDto;
import model.dto.UserDto;
import repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    public static boolean signUp(UserDto userData) throws SQLException {
        String password = userData.getPassword();
        String confirmPassword = userData.getConfirmPassword();

        if (!password.equals(confirmPassword))
            return false;

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

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getPasswordHash();

        if (PasswordHasher.compareSaltedHash(password, salt, passwordHash)) {
            SessionManager.setUser(UserRepository.getByEmail(loginData.getEmail()));
            return true;
        }
        // ska nevoj per else qitu
        return false;
    }
}
