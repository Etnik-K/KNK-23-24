package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import service.DBConnector;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link TableColumn with User properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
    }

    public void fetchDataFromDatabase() {
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

                userList.add(new User(id, firstName, lastName, email, null, null, user_type));
            }
            TableView.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
