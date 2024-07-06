//LoginService:
package service;

import app.Navigator;
import app.SessionManager;
import controller.popups.EmailPopupController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.dto.LoginUserDto;
import model.dto.UserDto;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInSignInService {
    private Stage stage;
    private String selectedRole;
    public void initializeLoginLabels(Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount, UserService userService) {
        ResourceBundle bundle = userService.getBundle();

        lblEmail.setText(bundle.getString("lblEmail"));
        lblPassword.setText(bundle.getString("lblPassword"));
        btnlogin.setText(bundle.getString("btnlogin"));
        btncancel.setText(bundle.getString("btncancel"));
        txtlogin.setText(bundle.getString("txtLogin"));
        txtWelcome.setText(bundle.getString("txtWelcome"));
        txtUniofPr.setText(bundle.getString("txtUniofPr"));
        txtLoginForInfo.setText(bundle.getString("txtLoginForInfo"));
        lblCreateAccount.setText(bundle.getString("lblCreateAccount"));
    }

    public void handleLoginClick(ActionEvent ae, String email, String password) throws SQLException, IOException {
        LoginUserDto loginUserData = new LoginUserDto(email, password);

        if (UserService.login(loginUserData)) {
            System.out.println("Imella: " + email);
            SessionManager.setUser(UserRepository.getByEmail(email));
            System.out.println("U bo set sessioni");
            User user = SessionManager.getUser();
            if (user != null) {
//                User user123 = SessionManager.getUser();
//                System.out.println("USER TYPE: " + user123.getUserType());
//                System.out.println("USER name: " + user123.getFirstName() + " " + user123.getLastName());

                if (user.getUserType().equals("professor")) {
                    Navigator.navigate(ae, Navigator.PROFESSOR_PAGE, "Login");
                    System.out.println("Logged in as Professor: " + user.getFirstName() + " " + user.getLastName());
                } else if (user.getUserType().equals("student")) {
                    Navigator.navigate(ae, Navigator.STUDENT_PAGE, "StudentView");
                    System.out.println("Logged in as Student: " + user.getFirstName() + " " + user.getLastName());
                } else {
                    Navigator.navigate(ae, Navigator.ADMIN_DASHBOARD, "AdminView");
                    System.out.println("Logged in as the Administrator");
                }
            }
        }
    }

    public static void handleCancel(TextField txtEmail, PasswordField pwdPassword) {
        txtEmail.clear();
        pwdPassword.clear();
    }

    public static void handleCreateAccount(MouseEvent me) throws IOException {
        Navigator.navigate(me, Navigator.CREATE_ACCOUNT_PAGE, "Sign Up");
    }

    public static void handleLanguageClickLoginPage(MouseEvent mouseEvent, Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount) {
        Locale newLocale;
        if (SessionManager.getLocale().getLanguage().equals("en")) {
            newLocale = Locale.of("sq");
        } else {
            newLocale = Locale.of("en", "US");
        }

        Navigator.changeLanguage(newLocale.toLanguageTag());
        SessionManager.setLocale(newLocale);

        LogInSignInService.updateLoginPageText(newLocale, lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
        System.out.println("Language: " + newLocale.getLanguage());
    }

    public static void updateLoginPageText(Locale locale, Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        lblEmail.setText(bundle.getString("lblEmail"));
        lblPassword.setText(bundle.getString("lblPassword"));
        btnlogin.setText(bundle.getString("btnlogin"));
        btncancel.setText(bundle.getString("btncancel"));
        txtlogin.setText(bundle.getString("txtLogin"));
        txtWelcome.setText(bundle.getString("txtWelcome"));
        txtUniofPr.setText(bundle.getString("txtUniofPr"));
        txtLoginForInfo.setText(bundle.getString("txtLoginForInfo"));
        lblCreateAccount.setText(bundle.getString("lblCreateAccount"));
    }

    public void initKeyActions(Scene scene, Stage stage, Button btnlogin, String email, String password) {
        this.stage = stage;
        scene.setOnKeyPressed(keyAction -> {
            if (keyAction.getCode() == KeyCode.ENTER) {
                try {
                    // Krijo një ActionEvent të ri në vend që të kaloni null
                    handleLoginClick(new ActionEvent(btnlogin, null), email, password);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void initKeyActions(Scene scene, Stage stage, String txtFirstName, String email, String password) {
//        this.stage = stage;
//        scene.setOnKeyPressed(keyAction -> {
//            if (keyAction.getCode() == KeyCode.ENTER) {
//                try {
//                    // Krijo një ActionEvent të ri në vend që të kaloni null
//                    handleSignUp(new ActionEvent(txtFirstName, null), email, password);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    //SignUp part:

    public static void handleCancel(TextField txtFirstName, TextField txtLastName, PasswordField pwdPassword, PasswordField pwdConfirmPassword){
        txtFirstName.clear();
        txtLastName.clear();
        pwdPassword.clear();
        pwdConfirmPassword.clear();
    }
    public void handleSignUp(ActionEvent ae,TextField txtFirstName, TextField txtLastName, PasswordField pwdPassword, PasswordField pwdConfirmPassword) throws SQLException, IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = generateEmail(firstName, lastName);
        String password = pwdPassword.getText();
        String confirmPassword = pwdConfirmPassword.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            try {
                // Load the Denied.fxml file
                FXMLLoader loader = new FXMLLoader(UserService.class.getResource("/app/fill.fxml"));
                Parent root = loader.load();

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Fill all fields");
                //
//                Nuk t'len me prek kurgjo mrena faqes login deri sa ta mshel ket popupfile
                stage.initModality(Modality.APPLICATION_MODAL);


                stage.showAndWait(); // Show and wait until the new stage is closed
            } catch (IOException e) {
                e.printStackTrace(); // Handle error loading FXML file
            }
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
    public void initializeSignUpLabels(ResourceBundle bundle, Text txtSingUpMeInfo, Text txtThankYou, Text txtFirstNameL, Text txtLastNameL, Text txtPasswordL, Text txtConfirmPassword, Button btnSignup, Button btnCancel, Text txtSignUp) {
        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btnCancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
    }
    public void handleStudentClick(String selectedRole) {
        System.out.println("Student clicked");
        this.selectedRole = "Student";
    }
    public void updateSplitMenuButtonText(SplitMenuButton splitMenuButton, String selectedRole) {
        splitMenuButton.setText(selectedRole);
    }
    public void handleProfessorClick(String selectedRole) {
        System.out.println("Professor clicked");
        this.selectedRole = "Professor";
    }
    public void handleLanguageClickSignUpPage(MouseEvent mouseEvent, Text txtSingUpMeInfo, Text txtThankYou, Text txtFirstNameL, Text txtLastNameL, Text txtPasswordL, Text txtConfirmPassword, Button btnSignup, Button btnCancel, Text txtSignUp) {
        Locale newLocale = SessionManager.getNewLocale();

        Navigator.changeLanguage(newLocale.toLanguageTag());
        SessionManager.setLocale(newLocale);

        updateSignUpPageText(newLocale, txtSingUpMeInfo, txtThankYou, txtFirstNameL, txtLastNameL, txtPasswordL, txtConfirmPassword, btnSignup, btnCancel, txtSignUp);
        System.out.println("Language: " + newLocale.getLanguage());
    }

    public void updateSignUpPageText(Locale locale, Text txtSingUpMeInfo, Text txtThankYou, Text txtFirstNameL, Text txtLastNameL, Text txtPasswordL, Text txtConfirmPassword, Button btnSignup, Button btnCancel, Text txtSignUp) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        txtSingUpMeInfo.setText(bundle.getString("txtSingUpMeInfo"));
        txtThankYou.setText(bundle.getString("txtThankYou"));
        txtFirstNameL.setText(bundle.getString("txtFirstNameL"));
        txtLastNameL.setText(bundle.getString("txtLastNameL"));
        txtPasswordL.setText(bundle.getString("txtPasswordL"));
        txtConfirmPassword.setText(bundle.getString("txtConfirmPassword"));
        btnSignup.setText(bundle.getString("btnSignup"));
        btnCancel.setText(bundle.getString("btncancel"));
        txtSignUp.setText(bundle.getString("txtSignUp"));
    }
    public String getSelectedRole() {
        return selectedRole;
    }

}
