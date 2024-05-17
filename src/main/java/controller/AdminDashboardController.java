package controller;

import app.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import service.DBConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private double xOffset = 0;
    private double yOffset = 0;

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
    }

    @FXML
    private void handleAdd(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.ADMIN_ADD));
        Pane pane = loader.load();
        resultContainer.getChildren().clear();
        resultContainer.getChildren().add(pane);
    }

    @FXML
    private void handleLogOut(ActionEvent ae) {
        // Implement logout functionality
    }

    @FXML
    private void handleEdit(ActionEvent ae) {
        // Implement edit functionality
    }

    @FXML
    private void handleView(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/user_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            UserTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleMonday(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(Navigator.MONDAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTuesday(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(Navigator.TUESDAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWednesday(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(Navigator.WEDNESDAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleThursday(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(Navigator.THURSDAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFriday(ActionEvent ae) {
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(Navigator.FRIDAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void handleApprove(ActionEvent actionEvent) {
    }

    public void handleDeny(ActionEvent actionEvent) {
    }
}

