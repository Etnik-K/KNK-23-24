package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboardController {

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
