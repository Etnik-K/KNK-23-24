module com.example.knk2324 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.knk2324 to javafx.fxml;
    exports com.example.knk2324;

    exports app;
    opens controller to javafx.fxml;
}