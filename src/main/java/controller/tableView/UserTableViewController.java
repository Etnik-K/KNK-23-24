package controller.tableView;

import app.Navigator;
import app.SessionManager;
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
import javafx.scene.control.TextField;
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
    TableView<User> TableView;

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
             PreparedStatement statement = connection.prepareStatement(query)){


            ResultSet resultSet = statement.executeQuery();
            ObservableList<User> userList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String user_type = resultSet.getString("user_type");

                userList.add(new User(id, firstName, lastName, email, null, null, user_type, false));
                TableView.setItems(userList);
            }

        } catch (SQLException e) {
            System.out.println("An SQL exception occurred: " + e.getMessage());
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

                // Update professor table if the user is a professor
                if ("professor".equals(selectedUser.getUserType())) {
                    try (PreparedStatement updateProfessorStatement = connection.prepareStatement(queryUpdateProfessor)) {
                        updateProfessorStatement.setBoolean(1, true);
                        updateProfessorStatement.setInt(2, selectedUser.getId());
                        updateProfessorStatement.executeUpdate();
                    }
                }

                SessionManager.setUser(selectedUser);
                try {
                    FXMLLoader fxmlLoader;
                    if ("professor".equals(selectedUser.getUserType())) {
                        fxmlLoader = new FXMLLoader(getClass().getResource(Navigator.PFL));
                    } else if ("student".equals(selectedUser.getUserType())) {
                        fxmlLoader = new FXMLLoader(getClass().getResource(Navigator.STUDENT_F));
                    } else {
                        // Handle other user types or show a default FXML
                        fxmlLoader = new FXMLLoader(getClass().getResource(Navigator.LOGIN_PAGE));
                    }

                    Parent root = fxmlLoader.load();
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setTitle("Approval");

                    Scene scene = new Scene(root);
                    popupStage.setScene(scene);
                    popupStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSave(TextField txtDrejtimi, TextField txtLenda) {
        System.out.println("Hapi 1");
        User selectedUser = SessionManager.getUser();
        int faculty_id = Integer.parseInt(txtDrejtimi.getText());
        int lenda_id = Integer.parseInt(txtLenda.getText());


        String queryUpdateProfessor = "UPDATE profesor SET faculty_id = ?, lenda_id = ? WHERE id = ?";
        System.out.println("Hapi 2");
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement updateProfessorStatement = connection.prepareStatement(queryUpdateProfessor)) {

            updateProfessorStatement.setInt(1, faculty_id);
            updateProfessorStatement.setInt(2, lenda_id);
            updateProfessorStatement.setInt(3, selectedUser.getId());
            updateProfessorStatement.executeUpdate();

            // Close the pop-up window
            Stage stage = (Stage) txtDrejtimi.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("Hapi 3");
        fetchDataFromDatabase();
    }


    @FXML
    public void handleDeny(ActionEvent actionEvent) {
        User selectedUser = TableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String queryUpdate = "UPDATE users SET is_approved = ? WHERE id = ?";
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {

                // Set parameters for update statement
                updateStatement.setBoolean(1, true);
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
