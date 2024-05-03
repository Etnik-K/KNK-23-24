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
    private void handleSignUp(ActionEvent ae){
        UserDto userSignUpData = new UserDto(
                this.txtFirstName.getText(),
                this.txtLastName.getText(),
                this.txtEmail.getText(),
                this.pwdPassword.getText(),
                this.pwdConfirmPassword.getText()
        );
        System.out.println("Kemi mrri ne kete hap");
        boolean response = UserService.signUp(userSignUpData);
        System.out.println("Response: " + response);

        Navigator.navigate(ae, Navigator.LOGIN_PAGE);
    }

    @FXML
    private void handleCancel(ActionEvent ae){
        this.pwdConfirmPassword.clear();
        this.txtEmail.clear();
        this.txtFirstName.clear();
        this.txtLastName.clear();
        this.pwdPassword.clear();

    }
}
