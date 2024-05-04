package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProfessorController {

    @FXML
    private VBox resultContainer;


    @FXML
    private void handleLogOut(ActionEvent ae){

    }  @FXML
    private void handleSearch(ActionEvent ae){

    }  @FXML
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
    private void handleView(ActionEvent ae){

    }
}
