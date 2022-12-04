package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.exception.NoEmployeeByLoginException;
import com.example.app.exception.NoRoleByIdException;
import com.example.app.service.EmployeeService;
import com.example.app.service.UserService;
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
    private final UserService userService = new UserService();
    private final EmployeeService employeeService = new EmployeeService();
    @FXML
    private Label name;
    @FXML
    private Label post;
    @FXML
    private Button isActiveButton;

    public void setInfo(Employee employee_) {
        employee = employee_;
        name.setText(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
        post.setText(employee.getPost());

        if (employee.isActive()) {
            isActiveButton.setText("Деактивация");
        } else {
            isActiveButton.setText("Активация");
        }
    }

    @FXML
    public void isActiveButtonClick() {
        try {
            if (employee.getRoleId() == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Деактивировать администратора плохая идея 🤡", ButtonType.OK);
                alert.show();
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
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onEditButtonClick() {

    }

    @FXML
    public void onInfoButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employee-card-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EmployeeCardController controller = fxmlLoader.getController();
            controller.setInfo(employee);
            Stage stage = new Stage();
            stage.setTitle("Сотрудник");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | NoRoleByIdException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
}
