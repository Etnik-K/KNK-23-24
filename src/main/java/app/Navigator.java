package app;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Navigator {
    public final static String LOGIN_PAGE = "/app/login.fxml";
    public final static String HOME_PAGE = "home.fxml";
    public final static String CREATE_ACCOUNT_PAGE = "create_user_form.fxml";
    public final static String ADMIN_DASHBOARD="admin_dashboard.fxml";
    public final static String STUDENT_PAGE="student.fxml";
    public final static String PROFESSOR_PAGE="professor.fxml";
    public final static String ADMIN_ADD = "/app/admin_add.fxml";
    public final static String NEW_CLASS ="/app/new_class.fxml";
    ;

    public final static String MONDAY = "Query per diten e Hane";
    public final static String TUEDAY = "Query per diten e Marte";
    public final static String WEDNESDAY = "Query per diten e Merkure";
    public final static String THURSDAY = "Query per diten e Enjte";
    public final static String FRIDAY = "Query per diten e Premte";


    public static void navigate(Stage stage, String page){
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(page)
        );

        try{
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static void navigate(Event event, String page) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        navigate(stage, page);
    }
    public static void displayResults(String query, Pane resultContainer) {

        //Keta e fusim ne funksion masi ti bojm tabelat me orare
       // try (Connection connection = DBConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            resultContainer.getChildren().clear();
//            while (resultSet.next()) {
//                String resultText = resultSet.getString("PROVE PROVE"); // Replace column_name
//                resultContainer.getChildren().add(new Label(resultText));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        resultContainer.getChildren().clear();
        resultContainer.getChildren().add(new Label(query));
    }

    public static void refreshCurrentPage(Node node, String form) {
        if (node instanceof Pane) {
            Pane pane = (Pane) node;
            pane.getChildren().clear();
            Pane formPane = loadPane(form);
            pane.getChildren().add(formPane);
        }
    }

    public static String changeLanguage(String language){
        Locale locale = Locale.of("language");
        Locale.setDefault(locale);
        return language;
    }
    private static Pane loadPane(String form){

        ResourceBundle bundle = ResourceBundle.getBundle(
                "translations.content", Locale.getDefault()
        );
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(form), bundle
        );
        try {
            return loader.load();
        }catch (IOException ioe){
            return null;
        }
    }

}
