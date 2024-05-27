package service;

import app.SessionManager;
import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import repository.SFCRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SFCService {
    UserTableViewController utvc = new UserTableViewController();
    public void handleSave(ActionEvent actionEvent, TextField txtFaculty) {
        System.out.println("Step 1");
        User selectedUser = SessionManager.getUser();
        int faculty_id = Integer.parseInt(txtFaculty.getText());

        SFCRepository.handleSaveRepo(faculty_id, selectedUser, txtFaculty);

        System.out.println("Step 3");
        utvc.fetchDataFromDatabase();
    }
}
