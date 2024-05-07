package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dto.UserDto;

public class CreateUserController {
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
    private String selectedRole;

    @FXML
    private void handleCreate(ActionEvent ae){
        UserDto user = new UserDto(
                this.txtFirstName.getText(),
                this.txtLastName.getText(),
                this.txtEmail.getText(),
                this.pwdPassword.getText(   ),
                this.pwdConfirmPassword.getText(),
                this.selectedRole
        );
       // boolean userCreated = UserService.createUser(user);
    }

    @FXML
    private void handleCancel(ActionEvent ae){
        this.txtFirstName.clear();
        this.txtFirstName.clear();
        this.txtLastName.clear();
        this.txtEmail.clear();
        this.pwdPassword.clear();
        this.pwdConfirmPassword.clear();

    }

}
