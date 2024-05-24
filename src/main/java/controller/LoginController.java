package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.User;
import model.dto.LoginUserDto;
import service.UserService;
import app.SessionManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblCreateAccount;
    @FXML
    private Button btnlogin;
    @FXML
    private Button btncancel;
    @FXML
    private Text txtlogin;
    @FXML
    private Text txtWelcome;
    @FXML
    private Text txtUniofPr;
    @FXML
    private Text txtLoginForInfo;
    private final UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService userService = new UserService();
        userService.initializeLoginLabels(lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
    }

    @FXML
    private void handleLoginClick(ActionEvent ae) throws SQLException {
        String email = txtEmail.getText().trim();
        String password = pwdPassword.getText().trim();
        userService.handleLoginClick(ae, email, password);
    }

    @FXML
    private void handleCancelClick(ActionEvent ae){
        UserService userService = new UserService();
        userService.handleCancel(txtEmail, pwdPassword);
    }

    @FXML
    public void handleCreateAccountClick(MouseEvent me){
        UserService userService = new UserService();
        userService.handleCreateAccount(me);
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        UserService userService = new UserService();
        userService.handleLanguageClickLoginPage(mouseEvent, lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
    }

    public void loginEnter(KeyEvent keyEvent) {
    }
}

