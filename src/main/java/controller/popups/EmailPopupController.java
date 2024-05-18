package controller.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EmailPopupController {
    @FXML
    private Label lblGeneratedEmail;

    public void setGeneratedEmail(String email) {
        lblGeneratedEmail.setText("Generated Email: " + email);
    }
}