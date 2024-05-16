package controller;

import app.Navigator;
import database.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.dto.UserDto;
import service.DBConnector;
import service.UserService;

import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
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
    private Text txtSingUpMeInfo;
    @FXML
    private Text txtThankYou;
    @FXML
    private Text txtFirstNameL;
    @FXML
    private Text txtLastNameL;
    @FXML
    private Text txtEmailL;
    @FXML
    private Text txtPasswordL;
    @FXML
    private Text txtConfirmPassword;
    @FXML
    private Button btnSignup;
    @FXML
    private Button btncancel;
    //SplitButtonit sbon me ja ndrru fx:id sepse perdoret ma vone dikun tjeter qajo ID kur tregjistrohet
    /*@FXML
    private SplitMenuButton splitmenubuttonStatusi;*/
    @FXML
    private Text txtSignUp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtEmailL.setText(bundle.getString("txtEmailL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btncancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
       // splitmenubuttonStatusi.setText(bundle.getString("splitmenubuttonStatusi"));
    }



    @FXML
    private void handleSignUp(ActionEvent ae) throws SQLException {
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


    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        Locale newLocale;
        if (Locale.getDefault().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        // Change the language
        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);

        // Update the text of all elements
        updateText(newLocale);
        System.out.println("Gjuha: " + newLocale.getLanguage());
    }
    private void updateText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtEmailL.setText(bundle.getString("txtEmailL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btncancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
        //splitmenubuttonStatusi.setText(bundle.getString("splitmenubuttonStatusi"));
    }
}