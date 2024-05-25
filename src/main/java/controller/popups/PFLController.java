package controller.popups;

import controller.tableView.UserTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.UserService;

public class PFLController {
    @FXML
    private Text txtDrejtimiL;
    @FXML
    private Text txtLendaL;
    @FXML
    private TextField txtDrejtimi;
    @FXML
    private TextField txtLenda;
    private UserTableViewController utvc = new UserTableViewController();
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
        utvc.handleSave(txtDrejtimi,txtLenda);
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
