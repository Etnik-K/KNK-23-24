package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Navigator.navigate(stage, Navigator.LOGIN_PAGE, "Login");
//        Navigator.navigate(stage, Navigator.STUDENT_PAGE, "Admin View");
        Navigator.navigate(stage, Navigator.ADMIN_DASHBOARD, "Professor");
//        Navigator.navigate(stage, "PieChartPerSalla.fxml",  "Pie Chart");
    }

}