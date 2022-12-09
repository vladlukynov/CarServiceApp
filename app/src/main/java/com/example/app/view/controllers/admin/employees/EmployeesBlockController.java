package com.example.app.view.controllers.admin.employees;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.exception.NoEmployeeByLoginException;
import com.example.app.exception.NoRoleByIdException;
import com.example.app.service.EmployeeService;
import com.example.app.service.UserService;
import com.example.app.view.controllers.admin.AdminController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeesBlockController {
    private Employee employee;
    private AdminController adminController;
    private final UserService userService = new UserService();
    private final EmployeeService employeeService = new EmployeeService();
    @FXML
    private Label name;
    @FXML
    private Label post;
    @FXML
    private Button isActiveButton;

    public void setInfo(Employee employee_, AdminController controller) {
        adminController = controller;
        employee = employee_;
        name.setText(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
        post.setText(employee.getPost());

        isActiveButton.setText(employee.isActive() ? "Деактивация" : "Активация");
    }

    @FXML
    public void isActiveButtonClick() {
        try {
            if (employee.getRoleId() == 1) {
                new Alert(Alert.AlertType.INFORMATION, "Деактивировать администратора плохая идея 🤡", ButtonType.OK).show();
                return;
            }
            if (employee.isActive()) {
                userService.deactivateUser(employee.getUserLogin());
                isActiveButton.setText("Активация");
            } else {
                userService.activateUser(employee.getUserLogin());
                isActiveButton.setText("Деактивация");
            }
            employee = employeeService.getEmployeeInfo(employee.getUserLogin());
        } catch (SQLException | NoEmployeeByLoginException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employees/edit-employee-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EmployeeEditController controller = fxmlLoader.getController();
            controller.setInfo(employee, this, adminController);
            Stage stage = new Stage();
            stage.setTitle("Редактирование");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onInfoButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employees/employee-card-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EmployeeCardController controller = fxmlLoader.getController();
            controller.setInfo(employee);
            Stage stage = new Stage();
            stage.setTitle("Сотрудник");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | NoRoleByIdException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
