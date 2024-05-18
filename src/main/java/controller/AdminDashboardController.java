package controller;

import app.Navigator;
import app.SessionManager;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

    private UserTableViewController utvc;


    private double xOffset = 0;
    private double yOffset = 0;

    Navigator nav = new Navigator();
    //UserTableViewController utvc = new UserTableViewController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        // Set text for labels, buttons, text elements, etc.
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/user_table_view.fxml"));
            Parent userTableView = loader.load();
            utvc = loader.getController(); // Get the controller instance
            resultContainer.getChildren().add(userTableView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.ADMIN_ADD));
        Pane pane = loader.load();
        resultContainer.getChildren().clear();
        resultContainer.getChildren().add(pane);
    }

    @FXML
    private void handleLogOut(MouseEvent me) {
        SessionManager.setUser(null);
        Navigator.navigate(me, Navigator.LOGIN_PAGE, "Login");
    }

    @FXML
    private void handleEdit(ActionEvent ae) {
        // Implement edit functionality
    }

    @FXML
    private void handleView(ActionEvent ae) {
        // Check if the UserTableViewController is already initialized
        if (utvc != null) {
            // Fetch data from the database (if needed)
            utvc.fetchDataFromDatabase();
        }
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
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }
@FXML
    public void handleApproved(MouseEvent mouseEvent) {
    }

   /* @FXML
    public void handleApprove(ActionEvent actionEvent, TableView<User> tableView) {

    }



    @FXML
    public void handleDeny(ActionEvent actionEvent, TableView<User> tableView) {
        // Get the selected user from the table view

    }*/

    @FXML
    private void handleApprove(ActionEvent actionEvent) {
        // Handle approval action using UserTableViewController
        if (utvc != null) {
            utvc.handleApprove(actionEvent);
        }
    }


    @FXML
    private void handleDeny(ActionEvent actionEvent) {
        // Handle denial action using UserTableViewController
        if (utvc != null) {
            utvc.handleDeny(actionEvent);
        }
    }
}