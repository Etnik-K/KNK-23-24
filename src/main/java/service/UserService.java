package service;

import app.Navigator;
import app.RoomReservationAlgorithm;
import app.SessionManager;
import controller.popups.EmailPopupController;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
        if (SessionManager.getLocale().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        Navigator.changeLanguage(newLocale.toLanguageTag());
        SessionManager.setLocale(newLocale);
    }


    public void updateText(Locale locale, Button... buttons) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        buttons[0].setText(bundle.getString("btnMonday"));
        buttons[1].setText(bundle.getString("btnTuesday"));
        buttons[2].setText(bundle.getString("btnWednesday"));
        buttons[3].setText(bundle.getString("btnThursday"));
        buttons[4].setText(bundle.getString("btnFriday"));
    }
    public void updateText(Locale locale, Text txtSingUpMeInfo, Text txtThankYou, Text txtFirstNameL, Text txtLastNameL, Text txtPasswordL, Text txtConfirmPassword, Button btnSignup, Button btncancel, Text txtSignUp) {
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

    public boolean handleLogin(LoginUserDto loginUserData) throws SQLException {
        return login(loginUserData);
    }

    public ResourceBundle getBundle() {
        Locale locale = SessionManager.getLocale();
        return ResourceBundle.getBundle("translations.content", locale);
    }

    public void handleLanguageClickSignUpPage(MouseEvent mouseEvent, Text txtSingUpMeInfo, Text txtThankYou, Text txtFirstNameL, Text txtLastNameL, Text txtPasswordL, Text txtConfirmPassword, Button btnSignup, Button btnCancel, Text txtSignUp) {
        Locale newLocale;
        if (SessionManager.getLocale().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

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

    //NewClassController:
    public String getSelectedRole() {
        return selectedRole;
    }

    public void updateSplitMenuButtonText(SplitMenuButton splitMenuButton) {
        splitMenuButton.setText(selectedRole);
    }

    public void updateSplitMenuButtonText(SplitMenuButton splitMenuButton, String selectedRole) {
        splitMenuButton.setText(selectedRole);
    }


    public void handleDayClick(ActionEvent actionEvent, String day, SplitMenuButton splitMenuButton) {
        System.out.println(day + " clicked");
        selectedRole = day;
        updateSplitMenuButtonText(splitMenuButton);
    }
    public void handleCancel(TextField txtEmail, PasswordField pwdPassword) {
        txtEmail.clear();
        pwdPassword.clear();
    }

    public void handleCancel(TextField txtStudentsNumber, TextField txtStartTime, TextField txtEndTime, SplitMenuButton splitMenuButton) {
        selectedRole = "";
        updateSplitMenuButtonText(splitMenuButton);
        txtStudentsNumber.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
    }

    public void handleCancel(TextField txtFirstName, TextField txtLastName, PasswordField pwdPassword, PasswordField pwdConfirmPassword){
        txtFirstName.clear();
        txtLastName.clear();
        pwdPassword.clear();
        pwdConfirmPassword.clear();
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
    public void handleStudentClick(String selectedRole) {
        System.out.println("Student clicked");
        this.selectedRole = "Student";
    }

    public void handleProfessorClick(String selectedRole) {
        System.out.println("Professor clicked");
        this.selectedRole = "Professor";
    }
    public void handleHelp(String location) {
        // Load the FXML file for the help popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create a new stage for the help popup
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(new Scene(root));

        // Show the help popup stage
        helpStage.show();
    }
    public void handleDelete(TextField startTimeField, TextField endTimeField, TextField sallaField, TextField ditaField, AnchorPane anchor) {
        // Get the values from the text fields
        String startTimeText = startTimeField.getText();
        String endTimeText = endTimeField.getText();
        String sallaText = sallaField.getText();
        String ditaText = ditaField.getText();

        // Execute the delete query
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Orari WHERE start_time = ? AND end_time = ? AND salla_id = ? AND day_of_week = ?")) {

            // Set parameters for the prepared statement
            statement.setString(1, startTimeText);
            statement.setString(2, endTimeText);
            statement.setString(3, sallaText);
            statement.setString(4, ditaText);

            // Execute the delete statement
            statement.executeUpdate();
            anchor.setVisible(false);


            // Refresh the data in your table view or UI component
            // You may call a method in your main controller to refresh the UI
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exception
        }
    }

}
