package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import service.UserService;

public class NewClassController {

    @FXML
    private TextField txtStudentsNumber;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    @FXML
    private SplitMenuButton splitMenuButton;

    private final UserService userService = new UserService();

    @FXML
    private void updateSplitMenuButtonText() {
        userService.updateSplitMenuButtonText(splitMenuButton);
    }

    @FXML
    public void handleMondayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Monday", splitMenuButton);
    }

    @FXML
    public void handleTuesdayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Tuesday", splitMenuButton);
    }

    @FXML
    public void handleWednesdayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Wednesday", splitMenuButton);
    }

    @FXML
    public void handleThursdayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Thursday", splitMenuButton);
    }

    @FXML
    public void handleFridayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Friday", splitMenuButton);
    }

    @FXML
    public void handleSaturdayClick(ActionEvent actionEvent) {
        userService.handleDayClick(actionEvent, "Saturday", splitMenuButton);
    }

    @FXML
    private void handleCancel(ActionEvent ae) {
        userService.handleCancel(txtStudentsNumber, txtStartTime, txtEndTime, splitMenuButton);
    }

    @FXML
    private void handleSave(ActionEvent ae) {
        userService.handleSave(txtStudentsNumber, splitMenuButton, txtStartTime, txtEndTime);
    }
}
