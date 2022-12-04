package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.service.EmployeeService;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final EmployeeService employeeService = new EmployeeService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private VBox primaryLayout;

    @FXML
    void initialize() {
        Employee employee = (Employee) CarServiceApplication.getUser();

        nameLabel.setText(employee.getLastName() +
                " " + employee.getFirstName() +
                " " + employee.getMiddleName());
        userNameLabel.setText(CarServiceApplication.getUser().getUserLogin());
    }

    @FXML
    public void onEmployeesButtonClick() {
        try {
            List<Employee> employees = employeeService.getEmployeesInfo();
            employees.sort((o1, o2) -> o1.getLastName().compareToIgnoreCase(o2.getLastName()));

            Button addButton = new Button();
            addButton.setText("Регистрация сотрудника");
            addButton.setOnAction(event -> onAddButtonClick());
            primaryLayout.getChildren().clear();
            primaryLayout.getChildren().add(addButton);
            for (Employee employee : employees) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employees-block-view.fxml"));
                Node node = fxmlLoader.load();
                EmployeesBlockController employeesBlockController = fxmlLoader.getController();
                employeesBlockController.setInfo(employee);

                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void onAddButtonClick() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/add-employee-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EmployeeAddController controller = fxmlLoader.getController();
            controller.setInfo(this);
            stage.setScene(scene);
            stage.setTitle("Регистрация сотрудника");
            stage.setResizable(false);

            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
}
