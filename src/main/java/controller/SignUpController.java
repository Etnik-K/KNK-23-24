//SignUpController
package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.LogInSignInService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    // Removed txtEmail
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private PasswordField pwdConfirmPassword;
    @FXML
    private SplitMenuButton splitMenuButton;
    @FXML
    private String selectedRole;
    @FXML
    private Text txtSingUpMeInfo;
    @FXML
    private Text txtThankYou;
    @FXML
    private Text txtFirstNameL;
    @FXML
    private Text txtLastNameL;
    // Removed txtEmailL
    @FXML
    private Text txtPasswordL;
    @FXML
    private Text txtConfirmPassword;
    @FXML
    private Button btnSignup;
    @FXML
    private Button btncancel;
    @FXML
    private Text txtSignUp;

    // UserService userService = new UserService();
    LogInSignInService logInSignInService = new LogInSignInService();
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content",
                new Locale(Navigator.changeLanguage("sq")));
        logInSignInService.initializeSignUpLabels(bundle, txtSingUpMeInfo, txtThankYou, txtFirstNameL, txtLastNameL,
                txtPasswordL, txtConfirmPassword, btnSignup, btncancel, txtSignUp);
    }

    @FXML
    private void handleSignUp(ActionEvent ae) throws SQLException, IOException {
        logInSignInService.handleSignUp(ae,txtFirstName, txtLastName, pwdPassword, pwdConfirmPassword);
    }
    @FXML
    private void handleCancel(ActionEvent ae) {
        logInSignInService.handleCancel(txtFirstName, txtLastName, pwdPassword, pwdConfirmPassword);
    }

    @FXML
    private void handleStudentClick(ActionEvent ae) {
        logInSignInService.handleStudentClick(selectedRole);
        logInSignInService.updateSplitMenuButtonText(splitMenuButton, logInSignInService.getSelectedRole());
    }

    @FXML
    private void handleProfessorClick(ActionEvent ae) {
        logInSignInService.handleProfessorClick(selectedRole);
        logInSignInService.updateSplitMenuButtonText(splitMenuButton, logInSignInService.getSelectedRole());
    }


    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        logInSignInService.handleLanguageClickSignUpPage(mouseEvent, txtSingUpMeInfo, txtThankYou, txtFirstNameL,
                txtLastNameL, txtPasswordL, txtConfirmPassword, btnSignup, btncancel, txtSignUp);
    }

    @FXML
    //Gent ki nderin dhe kenaqesine me ndreq qeta
    public void initKeyActions(Scene scene, Stage stage) {
        this.stage = stage;
        scene.setOnKeyPressed(keyAction -> {
            if (keyAction.getCode() == KeyCode.ENTER) {
                try {
                    // Krijo një ActionEvent të ri në vend që të kaloni null
                    handleSignUp(new ActionEvent(txtFirstName, null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
