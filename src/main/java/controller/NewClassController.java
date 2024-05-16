package controller;

import app.RoomReservationAlgorithm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import java.sql.Time;
import java.time.LocalDate;

public class NewClassController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblEndTime;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label lblStartTime;

    @FXML
    private Label lblStudentsNumber;

    @FXML
    private TextField txtStudentsNumber;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    @FXML
    private SplitMenuButton splitMenuButton;

    private String selectedRole;

    @FXML
    private void updateSplitMenuButtonText() {
        splitMenuButton.setText(selectedRole);
    }

    @FXML
    public void handleMondayClick(ActionEvent actionEvent) {
        System.out.println("Monday clicked");
        selectedRole = "Monday";
        updateSplitMenuButtonText();
    }

    @FXML
    public void handleTuesdayClick(ActionEvent actionEvent) {
        System.out.println("Tuesday clicked");
        selectedRole = "Tuesday";
        updateSplitMenuButtonText();
    }

    @FXML
    public void handleWednesdayClick(ActionEvent actionEvent) {
        System.out.println("Wednesday clicked");
        selectedRole = "Wednesday";
        updateSplitMenuButtonText();
    }

    @FXML
    public void handleThursdayClick(ActionEvent actionEvent) {
        System.out.println("Thursday clicked");
        selectedRole = "Thursday";
        updateSplitMenuButtonText();
    }

    @FXML
    public void handleFridayClick(ActionEvent actionEvent) {
        System.out.println("Friday clicked");
        selectedRole = "Friday";
        updateSplitMenuButtonText();
    }

    @FXML
    public void handleSaturdayClick(ActionEvent actionEvent) {
        System.out.println("Saturday clicked");
        selectedRole = "Saturday";
        updateSplitMenuButtonText();
    }

    @FXML
    private void handleCancel(ActionEvent ae) {
        selectedRole = "";
        updateSplitMenuButtonText();
       // datePicker.setValue(null);
        //endDatePicker.setValue(null);
        txtStudentsNumber.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
    }

    public TextField getTxtStudentsNumber() {
        return txtStudentsNumber;
    }

    public TextField getTxtEndTime() {
        return txtEndTime;
    }

    public TextField getTxtStartTime() {
        return txtStartTime;
    }

    public SplitMenuButton getSplitMenuButton() {
        return splitMenuButton;
    }

    public String getSelectedRole() {
        return selectedRole;
    }
    public int getNumStudents() {
        try {
            return Integer.parseInt(txtStudentsNumber.getText());
        } catch (NumberFormatException e) {

            e.printStackTrace();
            return -1;
        }
    }

    public String getDayOfWeek() {
        return splitMenuButton.getText();
    }

    public Time getStartTime() {
        try {
            return Time.valueOf(txtStartTime.getText());
        } catch (Exception e) {
            // Handle the case where the input is not a valid time
            e.printStackTrace();
            return null; // or some default value
        }
    }

    public Time getEndTime() {
        try {
            return Time.valueOf(txtEndTime.getText());
        } catch (Exception e) {
            // Handle the case where the input is not a valid time
            e.printStackTrace();
            return null; // or some default value
        }
    }

    @FXML
    private void handleSave(ActionEvent ae) {
        int numStudents = getNumStudents();
        String dayOfWeek = getDayOfWeek();
        Time startTime = getStartTime();
        Time endTime = getEndTime();


        if (numStudents > 0 && dayOfWeek != null && startTime != null && endTime != null) {
            RoomReservationAlgorithm.reserveRoom(numStudents, dayOfWeek, startTime, endTime);
        } else {
            System.out.println("Invalid input. Please check your input values.");
        }
    }


}
