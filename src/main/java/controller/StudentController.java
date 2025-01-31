package controller;


import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Orari;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

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
    private UserService userService = new UserService();

    Navigator nav = new Navigator();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService.initializeDashboard(resultContainer, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);
    }

    @FXML
    private VBox resultContainer;

    @FXML
    private void handleLogOut(MouseEvent me) throws IOException {
        userService.handleLogOut();
        Navigator.navigate(me, Navigator.LOGIN_PAGE, "Login");
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
    private void handleSearch(ActionEvent ae){}
    @FXML
    private void handleView(ActionEvent ae) {
        nav.displayOrariTableView(resultContainer, Navigator.ALL);
    }

    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        userService.handleLanguageClick();
        Locale defaultLocale = Locale.getDefault();
        userService.updateText(defaultLocale, btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday);
    }
    
    @FXML
    public void handleHelp(ActionEvent actionEvent) {
    userService.handleHelp(Navigator.STUDENT_HELP);
    }
}