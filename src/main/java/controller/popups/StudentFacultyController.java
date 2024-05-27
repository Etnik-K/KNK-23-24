package controller.popups;

import app.SessionManager;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.DBConnector;
import service.SFCService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentFacultyController {
    @FXML
    private TextField txtFaculty;
    public TextField getTxtFaculty() {
        return txtFaculty;
    }
    UserTableViewController utvc = new UserTableViewController();
    SFCService sfc = new SFCService();
    public void handleSave(ActionEvent actionEvent) {
        sfc.handleSave(actionEvent, txtFaculty);
    }
    public void handleCancel(ActionEvent actionEvent) {}
}