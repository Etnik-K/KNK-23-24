package controller.tableView;

import app.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import service.DBConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserTableViewController implements Initializable {

    @FXML
    private TableView<User> TableView;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> userTypeColumn;

    public TableView<User> getTableView() {
        return TableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link TableColumn with User properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));

        // Fetch data from the database and populate the TableView
        //fetchDataFromDatabase();
    }

    public void fetchDataFromDatabase() {
        String query = "SELECT id, firstName, lastName, email, user_type FROM users WHERE is_approved is false";
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

                userList.add(new User(id, firstName, lastName, email, null, null, user_type, null));
            }
            TableView.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleApprove(ActionEvent actionEvent) {
        User selectedUser = TableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String queryUpdateUser = "UPDATE users SET is_approved = ? WHERE id = ?";
            String queryUpdateProfessor = "UPDATE profesor SET is_approved = ? WHERE id = ?";
            try (Connection connection = DBConnector.getConnection()) {
                // Update users table
                try (PreparedStatement updateUserStatement = connection.prepareStatement(queryUpdateUser)) {
                    updateUserStatement.setBoolean(1, true);
                    updateUserStatement.setInt(2, selectedUser.getId());
                    updateUserStatement.executeUpdate();
                }

                // Update profesor table
                try (PreparedStatement updateProfessorStatement = connection.prepareStatement(queryUpdateProfessor)) {
                    updateProfessorStatement.setBoolean(1, true);
                    updateProfessorStatement.setInt(2, selectedUser.getId());
                    updateProfessorStatement.executeUpdate();
                }

                // Refresh the table view
                fetchDataFromDatabase();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Navigator.PFL));
                    Parent root = fxmlLoader.load();
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setTitle("Approval");

                    Scene scene = new Scene(root);
                    popupStage.setScene(scene);
                    popupStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Optionally, show a user-friendly error message
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Optionally, show a user-friendly error message
            }
        }
    }


    @FXML
    public void handleDeny(ActionEvent actionEvent) {
        User selectedUser = TableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String queryUpdate = "UPDATE users SET is_approved = ? WHERE id = ?";
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {

                // Set parameters for update statement
                updateStatement.setBoolean(1, false);
                updateStatement.setInt(2, selectedUser.getId());
                updateStatement.executeUpdate();

                // Refresh the table view
                fetchDataFromDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
