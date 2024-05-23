package service;

import app.Navigator;
import app.RoomReservationAlgorithm;
import app.SessionManager;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.dto.CreateUserDto;
import model.dto.LoginUserDto;
import model.dto.UserDto;
import repository.UserRepository;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserService {
    private UserTableViewController utvc;
    private Navigator nav = new Navigator();
    private String selectedRole;
    public static boolean signUp(UserDto userData) throws SQLException {
        String password = userData.getPassword();
        String confirmPassword = userData.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            return false;
        }

        String salt = PasswordHasher.generateSalt();
        String passwordHash = PasswordHasher.generateSaltedHash(password, salt);

        CreateUserDto createUserData = new CreateUserDto(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                salt,
                passwordHash,
                userData.getSelectedRole()
        );

        if (UserRepository.create(createUserData)) {
            SessionManager.setUser(UserRepository.getByEmail(userData.getEmail()));
            return true;
        }
        return false;
    }

    public static boolean login(LoginUserDto loginData) throws SQLException {
        User user = UserRepository.getByEmail(loginData.getEmail());
        if (user == null) {
            return false;
        }

        if (!user.isApproved()) {
            try {
                FXMLLoader loader = new FXMLLoader(UserService.class.getResource("/app/denied.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Access Denied");
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getPasswordHash();

        if (PasswordHasher.compareSaltedHash(password, salt, passwordHash)) {
            SessionManager.setUser(user);
            return true;
        }
        return false;
    }

    public static boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // New methods transferred from AdminDashboardController
    public void initializeDashboard(VBox resultContainer, Button... buttons) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        buttons[0].setText(bundle.getString("btnMonday"));
        buttons[1].setText(bundle.getString("btnTuesday"));
        buttons[2].setText(bundle.getString("btnWednesday"));
        buttons[3].setText(bundle.getString("btnThursday"));
        buttons[4].setText(bundle.getString("btnFriday"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/user_table_view.fxml"));
            Parent userTableView = loader.load();
            utvc = loader.getController();
            resultContainer.getChildren().add(userTableView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAdd(VBox resultContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.NEW_CLASS));
        Pane pane = loader.load();
        resultContainer.getChildren().clear();
        resultContainer.getChildren().add(pane);
    }

    public void handleLogOut() {
        SessionManager.setUser(null);
    }

    public void handleView() {
        if (utvc != null) {
            utvc.fetchDataFromDatabase();
        }
    }

    public void handleDayView(VBox resultContainer, String day) {
        nav.displayOrariTableView(resultContainer, day);
    }

    public void handleLanguageClick() {
        Locale newLocale;
        if (Locale.getDefault().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);
    }

    public void updateText(Locale locale, Button... buttons) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        buttons[0].setText(bundle.getString("btnMonday"));
        buttons[1].setText(bundle.getString("btnTuesday"));
        buttons[2].setText(bundle.getString("btnWednesday"));
        buttons[3].setText(bundle.getString("btnThursday"));
        buttons[4].setText(bundle.getString("btnFriday"));
    }

    public void handleApprove(ActionEvent actionEvent) {
        if (utvc != null) {
            utvc.handleApprove(actionEvent);
        }
    }

    public void handleDeny(ActionEvent actionEvent) {
        if (utvc != null) {
            utvc.handleDeny(actionEvent);
        }
    }

    // Methods transferred from LoginController
    public void initializeLoginLabels(Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

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

    public boolean handleLogin(LoginUserDto loginUserData) throws SQLException {
        return login(loginUserData);
    }

    public void handleCancel(TextField txtEmail, PasswordField pwdPassword) {
        txtEmail.clear();
        pwdPassword.clear();
    }

    public void handleCreateAccount(MouseEvent me) {
        Navigator.navigate(me, Navigator.CREATE_ACCOUNT_PAGE, "SignUP");
    }

    public void handleLanguageClickLoginPage(MouseEvent mouseEvent, Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount) {
        Locale newLocale;
        if (Locale.getDefault().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);

        updateLoginPageText(newLocale, lblEmail, lblPassword, btnlogin, btncancel, txtlogin, txtWelcome, txtUniofPr, txtLoginForInfo, lblCreateAccount);
        System.out.println("Gjuha: " + newLocale.getLanguage());
    }

    public void updateLoginPageText(Locale locale, Label lblEmail, Label lblPassword, Button btnlogin, Button btncancel, Text txtlogin, Text txtWelcome, Text txtUniofPr, Text txtLoginForInfo, Label lblCreateAccount) {
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
    public boolean handleLoginClick(ActionEvent ae, String email, String password) throws SQLException {
        LoginUserDto loginUserData = new LoginUserDto(email, password);

        boolean isLogin = login(loginUserData);
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
                    System.out.println("Logged in as Admin");
                }
                return true;
            }
        }
        return false;
    }

    //NewClassController:
    public String getSelectedRole() {
        return selectedRole;
    }

    public void updateSplitMenuButtonText(SplitMenuButton splitMenuButton) {
        splitMenuButton.setText(selectedRole);
    }

    public void handleDayClick(ActionEvent actionEvent, String day, SplitMenuButton splitMenuButton) {
        System.out.println(day + " clicked");
        selectedRole = day;
        updateSplitMenuButtonText(splitMenuButton);
    }

    public void handleCancel(TextField txtStudentsNumber, TextField txtStartTime, TextField txtEndTime, SplitMenuButton splitMenuButton) {
        selectedRole = "";
        updateSplitMenuButtonText(splitMenuButton);
        txtStudentsNumber.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
    }

    public int getNumStudents(TextField txtStudentsNumber) {
        try {
            return Integer.parseInt(txtStudentsNumber.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Time getTime(TextField txtTime) {
        try {
            return Time.valueOf(txtTime.getText());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handleSave(TextField txtStudentsNumber, SplitMenuButton splitMenuButton, TextField txtStartTime, TextField txtEndTime) {
        int numStudents = getNumStudents(txtStudentsNumber);
        String dayOfWeek = splitMenuButton.getText();
        Time startTime = getTime(txtStartTime);
        Time endTime = getTime(txtEndTime);

        if (numStudents > 0 && dayOfWeek != null && startTime != null && endTime != null) {
            RoomReservationAlgorithm.reserveRoom(numStudents, dayOfWeek, startTime, endTime);
        } else {
            System.out.println("Invalid input. Please check your input values.");
        }
    }
}
