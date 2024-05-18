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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btncancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
    }

    @FXML
    private void handleSignUp(ActionEvent ae) throws SQLException, IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = generateEmail(firstName, lastName);
        String password = pwdPassword.getText();
        String confirmPassword = pwdConfirmPassword.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Please fill in all the fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            try {
                // Load the Denied.fxml file
                FXMLLoader loader = new FXMLLoader(UserService.class.getResource("/app/NoMatch.fxml"));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Password do NOT match");
                //
//                Nuk t'len me prek kurgjo mrena faqes login deri sa ta mshel ket popupfile
                stage.initModality(Modality.APPLICATION_MODAL);


                stage.showAndWait(); // Show and wait until the new stage is closed
            } catch (IOException e) {
                e.printStackTrace(); // Handle error loading FXML file
            }
            return;
        }

        if (selectedRole == null || selectedRole.isEmpty()) {
            System.out.println("Please select a role.");
            return;
        }

        UserDto userSignUpData = new UserDto(firstName, lastName, email, password, confirmPassword, selectedRole);

        boolean response = UserService.signUp(userSignUpData);
        System.out.println("Response: " + response);

        // Display the generated email in a popup
        showGeneratedEmailPopup(email);

        Navigator.navigate(ae, Navigator.LOGIN_PAGE, "Login");
    }


    private String generateEmail(String firstName, String lastName) throws SQLException {
        String baseEmail = firstName + "." + lastName;
        String domain = selectedRole.equals("Student") ? "@student.uni-pr.edu" : "@uni-pr.edu";
        String email = baseEmail + domain;
        int counter = 1;

        while (UserService.emailExists(email)) {
            email = baseEmail + counter + domain;
            counter++;
        }
        return email;
    }
    private void showGeneratedEmailPopup(String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/EmailPopup.fxml"));
        Parent root = loader.load();

        EmailPopupController controller = loader.getController();
        controller.setGeneratedEmail(email);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Generated Email");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    private void handleCancel(ActionEvent ae) {
        txtFirstName.clear();
        txtLastName.clear();
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

        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);
        updateText(newLocale);
        System.out.println("Language: " + newLocale.getLanguage());
    }

    private void updateText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btncancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
    }
}
