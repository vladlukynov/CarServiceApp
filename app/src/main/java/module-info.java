module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.app to javafx.fxml;
    opens com.example.app.view.controllers.auth to javafx.fxml;
    opens com.example.app.view.controllers.admin to javafx.fxml;
    opens com.example.app.view.controllers.admin.employees to javafx.fxml;
    opens com.example.app.view.controllers.admin.cars to javafx.fxml;
    opens com.example.app.view.controllers.admin.services to javafx.fxml;
    opens com.example.app.view.controllers.admin.details to javafx.fxml;
    opens com.example.app.view.controllers.admin.orders to javafx.fxml;
    opens com.example.app.view.controllers.client to javafx.fxml;
    opens com.example.app.view.controllers.client.details to javafx.fxml;
    opens com.example.app.view.controllers.client.services to javafx.fxml;
    opens com.example.app.view.controllers.client.orders to javafx.fxml;
    opens com.example.app.view.controllers.client.cars to javafx.fxml;
    opens com.example.app.view.controllers.employee to javafx.fxml;
    opens com.example.app.view.controllers.employee.details to javafx.fxml;
    opens com.example.app.view.controllers.employee.services to javafx.fxml;

    exports com.example.app;
    exports com.example.app.entity;
    exports com.example.app.view.controllers.auth;
    exports com.example.app.view.controllers.admin;
    exports com.example.app.exception;
    exports com.example.app.view.controllers.admin.employees;
    exports com.example.app.view.controllers.admin.cars;
    exports com.example.app.view.controllers.admin.services;
    exports com.example.app.view.controllers.admin.details;
    exports com.example.app.view.controllers.admin.orders;
    exports com.example.app.view.controllers.client;
    exports com.example.app.view.controllers.client.details;
    exports com.example.app.view.controllers.client.services;
    exports com.example.app.view.controllers.client.orders;
    exports com.example.app.view.controllers.client.cars;
    exports com.example.app.view.controllers.employee;
    exports com.example.app.view.controllers.employee.details;
    exports com.example.app.view.controllers.employee.services;
}
