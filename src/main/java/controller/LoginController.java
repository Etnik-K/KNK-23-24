package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        // Set text for labels, buttons, text elements, etc.
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

    @FXML
    private void handleLoginClick(ActionEvent ae) throws SQLException {
        LoginUserDto loginUserData = new LoginUserDto(
                this.txtEmail.getText(),
                this.pwdPassword.getText()
        );

        boolean isLogin = UserService.login(loginUserData);
        if (isLogin) {
            String email = txtEmail.getText().trim();

            User user = SessionManager.getUser();

                if (user.getUserType().equals("professor")) {
                    Navigator.navigate(ae, Navigator.PROFESSOR_PAGE);
                    System.out.println("Logged in as Professor: " + user.getFirstName() + " " + user.getLastName());
                } else if (user.getUserType().equals("student")) {
                    Navigator.navigate(ae, Navigator.STUDENT_PAGE);
                    System.out.println("Logged in as Student: " + user.getFirstName() + " " + user.getLastName());
                }
             else {
                Navigator.navigate(ae, Navigator.ADMIN_DASHBOARD);
                System.out.println("Logged in as Admin");
            }
        }
    }



    @FXML
    private void handleCancelClick(ActionEvent ae){
            this.txtEmail.clear();
            this.pwdPassword.clear();
    }


    @FXML
    public void handleCreateAccountClick(MouseEvent me){
        Navigator.navigate(me, Navigator.CREATE_ACCOUNT_PAGE);
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
        lblEmail.setText(bundle.getString("lblEmail"));
        lblPassword.setText(bundle.getString("lblPassword"));
        btnlogin.setText(bundle.getString("btnlogin"));
        btncancel.setText(bundle.getString("btncancel"));
        txtlogin.setText(bundle.getString("txtLogin"));
        txtWelcome.setText(bundle.getString("txtWelcome"));
        txtUniofPr.setText(bundle.getString("txtUniofPr"));
        txtLoginForInfo.setText(bundle.getString("txtLoginForInfo"));
    }
}
