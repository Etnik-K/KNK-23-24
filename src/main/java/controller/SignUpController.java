package controller;

import app.Navigator;
import controller.popups.EmailPopupController;
import database.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dto.UserDto;
import service.DBConnector;
import service.UserService;

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

    UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));
        userService.initializeSignUpLabels(bundle, txtSingUpMeInfo, txtThankYou, txtFirstNameL, txtLastNameL, txtPasswordL, txtConfirmPassword, btnSignup, btncancel, txtSignUp);
    }

    @FXML
    private void handleSignUp(ActionEvent ae) throws SQLException, IOException {
        userService.handleSignUp(ae,txtFirstName, txtLastName, pwdPassword, pwdConfirmPassword);
    }
    @FXML
    private void handleCancel(ActionEvent ae) {
        userService.handleCancel(txtFirstName, txtLastName, pwdPassword, pwdConfirmPassword);
    }

    @FXML
    private void handleStudentClick(ActionEvent ae) {
        userService.handleStudentClick(selectedRole);
        userService.updateSplitMenuButtonText(splitMenuButton, userService.getSelectedRole());
    }

    @FXML
    private void handleProfessorClick(ActionEvent ae) {
        userService.handleProfessorClick(selectedRole);
        userService.updateSplitMenuButtonText(splitMenuButton, userService.getSelectedRole());
    }


    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        userService.handleLanguageClick();
        userService.updateText(Locale.getDefault(),txtSingUpMeInfo, txtThankYou, txtFirstNameL, txtLastNameL, txtPasswordL, txtConfirmPassword, btnSignup, btncancel, txtSignUp);
    }
}
