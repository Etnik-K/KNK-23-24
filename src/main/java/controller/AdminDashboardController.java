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
    private TableView<User> TableView;
    @FXML
    TableColumn<Object, Object> idColumn;
    @FXML
    TableColumn<Object, Object> firstNameColumn;
    @FXML
    TableColumn<Object, Object> lastNameColumn;
    @FXML
    TableColumn<Object, Object> emailColumn;
    @FXML
    TableColumn<Object, Object> userTypeColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        // Set text for labels, buttons, text elements, etc.
       // btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
    }

    private void fetchDataFromDatabase() {
        String query = "SELECT id, firstName, lastName, email, user_type FROM users WHERE is_approved = false";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            ObservableList<User> userList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String user_type = resultSet.getString("user_type");

               // System.out.println("id: " + id + ", firstName: " + firstName + ", lastName: " + lastName + ", email: " + email + ", user_type: " + user_type);


                userList.add(new User(id, firstName, lastName, email, null, null, user_type));
            }
            TableView.setItems(userList);
           // System.out.println(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @FXML
    private VBox resultContainer;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleAdd(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.ADMIN_ADD));
        Pane pane = loader.load();
        resultContainer.getChildren().clear();
        resultContainer.getChildren().add(pane);

    }

    @FXML
    private void handleApproved(ActionEvent ae){

    }
    @FXML
    private void handleLogOut(ActionEvent ae){

    }
    @FXML
    private void handleEdit(ActionEvent ae){

    }
        @FXML
        private void handleView(ActionEvent ae) {
            fetchDataFromDatabase();
        }
    @FXML
    private void handleMonday(ActionEvent ae){
        Navigator.displayResults(Navigator.MONDAY, resultContainer);
    }
    @FXML
    private void handleTuesday(ActionEvent ae) {
        Navigator.displayResults(Navigator.TUEDAY, resultContainer);
    }
    @FXML
    private void handleWednesday(ActionEvent ae){
        Navigator.displayResults(Navigator.WEDNESDAY, resultContainer);
    }

    @FXML
    private void handleThursday(ActionEvent ae){
        Navigator.displayResults(Navigator.THURSDAY, resultContainer);
    }

    @FXML
    private void handleFriday(ActionEvent ae){
        Navigator.displayResults(Navigator.FRIDAY, resultContainer);
    }

    @FXML
    private void handleSearch(ActionEvent ae){

    }
    @FXML
    private void handleTIK(ActionEvent ae){

    }

    @FXML
    private void handleIKS(ActionEvent ae){

    }
    @FXML
    private void handleEAR(ActionEvent ae){

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
        //btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }

    @FXML
    public void handleApprove(ActionEvent actionEvent) {
        User selectedUser = TableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String queryDelete = "DELETE FROM users WHERE id = ?";
            String queryInsert = "INSERT INTO approved_users (firstName, lastName, email, user_type, faculty_id, is_approved) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(queryDelete);
                 PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {

                // Set parameters for delete statement
                deleteStatement.setInt(1, selectedUser.getId());
                deleteStatement.executeUpdate();

                // Set parameters for insert statement
                insertStatement.setString(1, selectedUser.getFirstName());
                insertStatement.setString(2, selectedUser.getLastName());
                insertStatement.setString(3, selectedUser.getEmail());
                insertStatement.setString(4, selectedUser.getUserType());
                insertStatement.setInt(5, 758);
                insertStatement.setBoolean(6, true);
                insertStatement.executeUpdate();

                TableView.getItems().remove(selectedUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleDeny(ActionEvent actionEvent) {
        User selectedUser = TableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String queryDelete = "DELETE FROM users WHERE id = ?";
            String queryInsert = "INSERT INTO denied_users (firstName, lastName, email, user_type, faculty_id, is_approved) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(queryDelete);
                 PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {

                // Set parameters for delete statement
                deleteStatement.setInt(1, selectedUser.getId());
                deleteStatement.executeUpdate();

                // Set parameters for insert statement
                insertStatement.setString(1, selectedUser.getFirstName());
                insertStatement.setString(2, selectedUser.getLastName());
                insertStatement.setString(3, selectedUser.getEmail());
                insertStatement.setString(4, selectedUser.getUserType());
                insertStatement.setInt(5, 758);
                insertStatement.setBoolean(6, false); // Set is_approved to false for denied user
                insertStatement.executeUpdate();

                TableView.getItems().remove(selectedUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
}
