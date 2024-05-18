module com.example.knk2324 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.knk2324 to javafx.fxml;
    exports com.example.knk2324;

    opens model to javafx.fxml, javafx.base; // Open model package to javafx.fxml and javafx.base
    exports model;

    exports app;
    opens controller to javafx.fxml;

    opens controller.popups to javafx.fxml;
    exports controller.popups;
    opens controller.tableView to javafx.fxml;
}
