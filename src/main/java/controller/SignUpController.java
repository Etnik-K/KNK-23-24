package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
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
    private SplitMenuButton splitMenuButton;

    private String selectedRole;

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

        if (selectedRole == null || selectedRole.isEmpty()) {
            System.out.println("Please select a role.");
            return;
        }

        // Create UserDto object with selected role
        UserDto userSignUpData = new UserDto(firstName, lastName, email, password, confirmPassword, selectedRole);

        // Perform sign up
        boolean response = UserService.signUp(userSignUpData);
        System.out.println("Response: " + response);

        // Navigate
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

    @FXML
    private void handleStudentClick(ActionEvent ae) {
        System.out.println("Student clicked");
        selectedRole = "Student";
        updateSplitMenuButtonText();
    }

    @FXML
    private void handleProfessorClick(ActionEvent ae) {
        System.out.println("Professor clicked");
        selectedRole = "Professor";
        updateSplitMenuButtonText();
    }


    private void updateSplitMenuButtonText() {
        splitMenuButton.setText(selectedRole);
    }
}
