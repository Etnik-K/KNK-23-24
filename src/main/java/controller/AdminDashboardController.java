package controller;

import app.Navigator;
import app.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private Button btnview;

    @FXML
    private Button btnMonday;

    @FXML
    private Button btnTuesday;

    @FXML
    private Button btnWednesday;

    @FXML
    private Button btnThursday;

    @FXML
    private Button btnFriday;

    @FXML
    private VBox resultContainer;

    private UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService.initializeDashboard(resultContainer, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);
    }

    @FXML
    private void handleAdd(MouseEvent event) throws IOException {
        userService.handleAdd(resultContainer);
    }

    @FXML
    private void handleLogOut(MouseEvent me) throws IOException {
        userService.handleLogOut();
        Navigator.navigate(me, Navigator.LOGIN_PAGE, "Login");
    }

    @FXML
    private void handleEdit(ActionEvent ae) {
        // Implement edit functionality
    }

    @FXML
    private void handleView(ActionEvent ae) {
        userService.handleView();
    }

    @FXML
    private void handleMonday(ActionEvent ae) {
        userService.handleDayView(resultContainer, Navigator.MONDAY);
    }

    @FXML
    private void handleTuesday(ActionEvent ae) {
        userService.handleDayView(resultContainer, Navigator.TUESDAY);
    }

    @FXML
    private void handleWednesday(ActionEvent ae) {
        userService.handleDayView(resultContainer, Navigator.WEDNESDAY);
    }

    @FXML
    private void handleThursday(ActionEvent ae) {
        userService.handleDayView(resultContainer, Navigator.THURSDAY);
    }

    @FXML
    private void handleFriday(ActionEvent ae) {
        userService.handleDayView(resultContainer, Navigator.FRIDAY);
    }

    @FXML
    private void handleSearch(ActionEvent ae) {
        // Implement search functionality
    }

    @FXML
    private void handleTIK(ActionEvent ae) {
        // Implement TIK functionality
    }

    @FXML
    private void handleIKS(ActionEvent ae) {
        // Implement IKS functionality
    }

    @FXML
    private void handleEAR(ActionEvent ae) {
        // Implement EAR functionality
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        userService.handleLanguageClick();
        Locale locale = SessionManager.getLocale();
        userService.updateText(locale, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);
    }

    @FXML
    public void handleApproved(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleApprove(ActionEvent actionEvent) {
        userService.handleApprove(actionEvent);
    }

    @FXML
    private void handleDeny(ActionEvent actionEvent) {
        userService.handleDeny(actionEvent);
    }
    @FXML
    public void handleHelp(ActionEvent actionEvent) {
        // Load the FXML file for the help popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pfl.fxml"));
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
}
