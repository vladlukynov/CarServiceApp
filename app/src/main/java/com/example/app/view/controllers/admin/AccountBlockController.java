package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AccountBlockController {
    private AdminController adminController;
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
            AccountEditController controller = UIActions.createStage("admin/account-edit-view.fxml", "Редактирование",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(adminController);
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

    public void setInfo(AdminController controller) {
        this.adminController = controller;
    }
}
