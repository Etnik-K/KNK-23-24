package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboardController {

    @FXML
    private VBox resultContainer;

    @FXML
    private void handleAdd(ActionEvent ae){


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
    private void handleView(ActionEvent ae){


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

}
