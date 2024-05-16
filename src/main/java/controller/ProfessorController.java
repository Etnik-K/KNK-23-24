package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfessorController implements Initializable {
    @FXML
    private Button btnview;

    @FXML
    private Button btnMonday;

    @FXML
    private Button btnTuesday;

    @FXML
    private Button btnWednesday;

    @FXML
    private Button btnThursday;

    @FXML
    private Button btnFriday;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", new Locale(Navigator.changeLanguage("sq")));

        // Set text for labels, buttons, text elements, etc.
        // btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }

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
    @FXML
    public void handleLanguageClick(MouseEvent mouseEvent) {
        Locale newLocale;
        if (Locale.getDefault().getLanguage().equals("en")) {
            newLocale = new Locale("sq");
        } else {
            newLocale = new Locale("en", "US");
        }

        // Change the language
        Navigator.changeLanguage(newLocale.toLanguageTag());
        Locale.setDefault(newLocale);

        // Update the text of all elements
        updateText(newLocale);
        System.out.println("Gjuha: " + newLocale.getLanguage());
    }
    private void updateText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
        //btnview.setText(bundle.getString("btnview"));
        btnMonday.setText(bundle.getString("btnMonday"));
        btnTuesday.setText(bundle.getString("btnTuesday"));
        btnWednesday.setText(bundle.getString("btnWednesday"));
        btnThursday.setText(bundle.getString("btnThursday"));
        btnFriday.setText(bundle.getString("btnFriday"));
    }

    @FXML
    public void handleAdd(MouseEvent mouseEvent) {
        try {
            // Load the first FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.NEW_CLASS));
            Pane firstPane = loader.load();

            // Add the loaded content to the resultContainer VBox
            resultContainer.getChildren().setAll(firstPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
