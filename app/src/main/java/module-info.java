module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.codec;

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
    opens com.example.app.view.controllers.employee.orders to javafx.fxml;
    opens com.example.app.view.controllers.employee.orders.details to javafx.fxml;
    opens com.example.app.view.controllers.employee.orders.services to javafx.fxml;

    exports com.example.app;
    exports com.example.app.entity;
}
