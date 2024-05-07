package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dto.UserDto;
import service.UserService;

public class SignUpController {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private PasswordField pwdConfirmPassword;

    @FXML
    private void handleSignUp(ActionEvent ae) {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String password = pwdPassword.getText();
        String confirmPassword = pwdConfirmPassword.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Please fill in all the fields.");
            return;
        }


        if (!password.equals(confirmPassword)) {
            System.out.println("Password and confirm password do not match.");
            return;
        }


        UserDto userSignUpData = new UserDto(firstName, lastName, email, password, confirmPassword);


        boolean response = UserService.signUp(userSignUpData);
        System.out.println("Response: " + response);


        Navigator.navigate(ae, Navigator.LOGIN_PAGE);
    }

    @FXML
    private void handleCancel(ActionEvent ae) {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        pwdPassword.clear();
        pwdConfirmPassword.clear();
    }
}
