package app;

import controller.LoginController;
import controller.SignUpController;
import controller.tableView.OrariTableViewController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import service.UserService;

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
    public final static String PFL ="/app/pfl.fxml";
    public final static String STUDENT_HELP = "/app/student_help.fxml";
    public final static String PROFESOR_HELP = "/app/profesor_help.fxml";
    public final static String ADMIN_HELP = "/app/admin_help.fxml";
    public final static String STUDENT_F = "/app/student_faculty.fxml" ;
    public static int Faculty_id;
//    public static String ALL = "Where fakulteti_id = '";
//    public  static String MONDAY = "WHERE day_of_week = 'Monday' and fakulteti_id= '";
//    public  static String TUESDAY = "WHERE day_of_week='Tuesday' and fakulteti_id= '";
//    public  static String WEDNESDAY = "WHERE day_of_week='Wednesday' and fakulteti_id= '";
//    public  static String THURSDAY = "WHERE day_of_week='Thursday' and fakulteti_id= '";
//    public  static String FRIDAY = "WHERE day_of_week='Friday' and fakulteti_id= '";

    public static String ALL = "";
    public  static String MONDAY = "WHERE day_of_week = 'Monday'";
    public  static String TUESDAY = "WHERE day_of_week='Tuesday'";
    public  static String WEDNESDAY = "WHERE day_of_week='Wednesday'";
    public  static String THURSDAY = "WHERE day_of_week='Thursday'";
    public  static String FRIDAY = "WHERE day_of_week='Friday'";







    public static void navigate(Stage stage, String page, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(page));
        Scene scene = new Scene(loader.load());

        // Thirr funksionin initKeyActions nëse kontrolluesi është LoginController ose CreateUserController
        Object controller = loader.getController();
        if (controller instanceof LoginController) {
            ((LoginController) controller).initKeyActions(scene, stage);
        }
        else if (controller instanceof SignUpController) {
            ((SignUpController) controller).initKeyActions(scene, stage);
        }

        stage.setScene(scene);
        stage.setResizable(false);
        // Vendosja e ikonës së Universitetit të Prishtinës
        Image icon = new Image(UserService.class.getResourceAsStream("/images/University_of_Prishtina_logo.png"));
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.show();
    }



    public static void navigate(Event event, String page, String title) throws IOException {
        Stage stage;
        if (event != null) {
            Node node = (Node) event.getSource();
            stage = (Stage) node.getScene().getWindow();
        } else {
            // Nëse event është null, krijoni një Stage të ri ose merrni atë ekzistues
            stage = new Stage();
        }
        navigate(stage, page, title);
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
    ////////////////
    public static String changeLanguage(String languageTag) {
        Locale newLocale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(newLocale);
        return newLocale.getLanguage();
    }
    ///////////////////
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
    public void displayOrariTableView(VBox resultContainer, String day){
        try {
            // Load UserTableView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/orari_table_view.fxml"));
            System.out.println("KA MRRI QETU");
            Parent userTable = loader.load();

            // Get the controller from the loader
            OrariTableViewController controller = loader.getController();

            // Insert UserTableView into the pane
            resultContainer.getChildren().clear();
            resultContainer.getChildren().add(userTable);

            // Fetch data from the database (if needed)
            controller.fetchDataFromDatabase(day);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
