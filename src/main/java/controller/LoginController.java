package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.LogInSignInService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private final LogInSignInService logInSignInService = new LogInSignInService();
    private Stage stage;
    private static String password;
    private static String email;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logInSignInService.initializeLoginLabels(lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount, userService);
    }

    @FXML
    private void handleLoginClick(ActionEvent ae) throws SQLException, IOException {
        email = txtEmail.getText().trim();
        password = pwdPassword.getText().trim();
        logInSignInService.handleLoginClick(ae, email, password);
    }

//    @FXML
//    private void handleLoginClick(@FXML TextField txtEmail, @FXML PasswordField pwdPassword) throws SQLException, IOException {
//        email = txtEmail.getText().trim();
//        password = pwdPassword.getText().trim();
//        logInSignInService.handleLoginClick(ae, email, password);
//    }

    @FXML
    private void handleCancelClick(ActionEvent ae){
        LogInSignInService.handleCancel(txtEmail, pwdPassword);
    }

    @FXML
    public void handleCreateAccountClick(MouseEvent me) throws IOException {
        LogInSignInService.handleCreateAccount(me);
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        LogInSignInService.handleLanguageClickLoginPage(mouseEvent, lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
    }

    public void loginEnter(KeyEvent keyEvent) {
        // kur e prek enter mu bo login
        // kena me perfundu tu e thirr navigae ose najsen qitu.
    }

    @FXML
    public void initKeyActions(Scene scene, Stage stage) {
        logInSignInService.initKeyActions(scene, stage, btnlogin, email, password);
    }
}

