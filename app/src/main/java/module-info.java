module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.app to javafx.fxml;
    opens com.example.app.view.controllers.auth to javafx.fxml;
    opens com.example.app.view.controllers.admin to javafx.fxml;

    exports com.example.app;
    exports com.example.app.entity;
    exports com.example.app.view.controllers.auth;
    exports com.example.app.view.controllers.admin;
}
