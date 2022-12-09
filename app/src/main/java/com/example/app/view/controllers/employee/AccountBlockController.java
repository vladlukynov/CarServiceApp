package com.example.app.view.controllers.employee;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AccountBlockController {
    private EmployeeController employeeController;
    @FXML
    private Label nameLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label postLabel;

    @FXML
    public void onEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/account-edit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AccountEditController controller = fxmlLoader.getController();
            controller.setInfo(employeeController);

            Stage stage = new Stage();
            stage.setTitle("Редактирование");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void initialize() {
        Employee employee = (Employee) CarServiceApplication.getUser();

        nameLabel.setText("ФИО: " + employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
        loginLabel.setText("Логин: " + employee.getUserLogin());
        birthdayLabel.setText("Дата рождения: " + employee.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        phoneLabel.setText("Номер телефона: " + employee.getPhoneNumber());
        emailLabel.setText("Электронная почта: " + employee.getEmail());
        salaryLabel.setText("Заработная плата: " + employee.getSalary() + " руб.");
        postLabel.setText("Должность: " + employee.getPost());
    }

    public void setInfo(EmployeeController controller) {
        this.employeeController = controller;
    }
}
