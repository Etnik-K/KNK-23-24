package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService userService = new UserService();
        userService.initializeLoginLabels(lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
    }

    @FXML
    private void handleLoginClick(ActionEvent ae) throws SQLException, IOException {
        String email = txtEmail.getText().trim();
        String password = pwdPassword.getText().trim();
        userService.handleLoginClick(ae, email, password, stage);
    }

    @FXML
    private void handleCancelClick(ActionEvent ae){
        UserService userService = new UserService();
        userService.handleCancel(txtEmail, pwdPassword);
    }

    @FXML
    public void handleCreateAccountClick(MouseEvent me) throws IOException {
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
    @FXML
    public void initKeyActions(Scene scene, Stage stage) {
        this.stage = stage; // Corrected assignment
        scene.setOnKeyPressed(keyAction -> {
            if (keyAction.getCode() == KeyCode.ENTER) {
                try {
                    handleLoginClick(null);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
