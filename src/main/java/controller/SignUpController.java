package controller;

import app.Navigator;
import database.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import model.dto.UserDto;
import service.DBConnector;
import service.UserService;

import java.sql.*;

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
    @FXML
    private String selectedRole;

    @FXML
    private void handleSignUp(ActionEvent ae) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = generateEmail(firstName, lastName);
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


        UserDto userSignUpData = new UserDto(firstName, lastName, email, password, confirmPassword, selectedRole);


        boolean response = UserService.signUp(userSignUpData);
        System.out.println("Response: " + response);


        Navigator.navigate(ae, Navigator.LOGIN_PAGE);
    }

    @FXML
    private void initialize() {
        txtFirstName.textProperty().addListener((observable, oldValue, newValue) -> updateGeneratedEmail());
        txtLastName.textProperty().addListener((observable, oldValue, newValue) -> updateGeneratedEmail());
    }

    private void updateGeneratedEmail() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            String generatedEmail = generateEmail(firstName, lastName);
            txtEmail.setText(generatedEmail);
        } else {
            txtEmail.clear();
        }
    }

    private String generateEmail(String firstName, String lastName) {
        String email = "";
        if (selectedRole.equals("Student")) {
            email = firstName + "." + lastName + "@student.uni-pr.edu";
        } else if(selectedRole.equals("Professor")){
            email = firstName + "." + lastName + "@uni-pr.edu";
        }
        return email;
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