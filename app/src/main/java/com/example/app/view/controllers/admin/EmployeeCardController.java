package com.example.app.view.controllers.admin;

import com.example.app.entity.Employee;
import com.example.app.exception.NoRoleByIdException;
import com.example.app.utils.Roles;
import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeCardController {
    @FXML
    private Label loginLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label postLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label birthdayLabel;

    public void setInfo(Employee employee_) throws NoRoleByIdException {
        loginLabel.setText("Логин: " + employee_.getUserLogin());
        nameLabel.setText("ФИО: " + employee_.getLastName() + " " + employee_.getFirstName() + " " + employee_.getMiddleName());
        emailLabel.setText("Электронная почта: " + employee_.getEmail());
        phoneLabel.setText("Телефон: " + employee_.getPhoneNumber());
        roleLabel.setText("Роль в системе: " + Roles.getRoleName(employee_.getRoleId()));
        postLabel.setText("Должность: " + employee_.getPost());
        salaryLabel.setText("Зарплата: " + employee_.getSalary());
        birthdayLabel.setText("Дата рождения: " + employee_.getBirthday());
    }

    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }
}
