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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Orari;
import model.dto.OrariRecordDto;
import service.DBConnector;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfessorController implements Initializable {
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
    private javafx.scene.control.TableView<Orari> TableView;

    @FXML
    private VBox resultContainer;

    Navigator nav = new Navigator();
    private UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService.initializeDashboard(resultContainer, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);

    }

    @FXML
    private void handleLogOut(MouseEvent me) throws IOException {
        SessionManager.setUser(null);
        Navigator.navigate(me, Navigator.LOGIN_PAGE, "Login");
    }
    @FXML
    private void handleSearch(ActionEvent ae) {
        // Add your search logic here
    }

    @FXML
    private void handleMonday(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.MONDAY);
    }

    @FXML
    private void handleTuesday(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.TUESDAY);
    }

    @FXML
    private void handleWednesday(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.WEDNESDAY);
    }

    @FXML
    private void handleThursday(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.THURSDAY);
    }

    @FXML
    private void handleFriday(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.FRIDAY);
    }


    @FXML
    private void handleView(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.ALL);
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        userService.handleLanguageClick();
        Locale locale = SessionManager.getLocale();
        userService.updateText(locale, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);
    }

    @FXML
    public void handleAdd(MouseEvent mouseEvent) throws IOException {
        userService.handleAdd(resultContainer);
    }

    @FXML
    public void handleHelp(ActionEvent actionEvent) {
        userService.handleHelp(Navigator.PROFESOR_HELP);
    }
}