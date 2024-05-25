package controller.popups;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PFLController {
    @FXML
    private Text txtDrejtimiL;
    @FXML
    private Text txtLendaL;
    private TextField txtDrejtimi;
    @FXML
    private TextField txtLenda;

    public Text getTxtDrejtimiL() {
        return txtDrejtimiL;
    }

    public Text getTxtLendaL() {
        return txtLendaL;
    }

    public TextField getTxtDrejtimi() {
        return txtDrejtimi;
    }

    public TextField getTxtLenda() {
        return txtLenda;
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
