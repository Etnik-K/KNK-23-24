package service;

import app.Navigator;
import app.SessionManager;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.dto.LoginUserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginService {
    private Stage stage;
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

    public boolean handleLoginClick(ActionEvent ae, String email, String password) throws SQLException, IOException {
        LoginUserDto loginUserData = new LoginUserDto(email, password);

        boolean isLogin = UserService.login(loginUserData);
        if (isLogin) {
            User user = SessionManager.getUser();
            if (user != null) {
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
                return true;
            }
        }
        return false;
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
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        Navigator.changeLanguage(newLocale.toLanguageTag());
        SessionManager.setLocale(newLocale);

        LoginService.updateLoginPageText(newLocale, lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
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

}
