package com.example.app.view.controllers.admin;

import com.example.app.entity.Employee;
import com.example.app.service.EmployeeService;
import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class EmployeeAddController {
    private final EmployeeService employeeService = new EmployeeService();
    private AdminController adminController;
    @FXML
    private TextField loginLabel;
    @FXML
    private PasswordField passLabel;
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private TextField phoneLabel;
    @FXML
    private ComboBox<String> roleLabel;
    @FXML
    private TextField postLabel;
    @FXML
    private TextField salaryLabel;
    @FXML
    private DatePicker birthdayLabel;

    @FXML
    public void initialize() {
        roleLabel.getItems().addAll("Администратор", "Сотрудник");
    }

    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    @FXML
    public void onRegButtonClick(ActionEvent event) {
        String login = loginLabel.getText().trim();
        String pass = passLabel.getText();
        String[] name = nameLabel.getText().trim().split(" ");
        String email = emailLabel.getText().trim().trim();
        String phoneNumber = phoneLabel.getText().trim();
        int roleId = roleLabel.getSelectionModel().getSelectedIndex() + 1;
        double salary;
        String post = postLabel.getText().trim();
        LocalDate birthday = birthdayLabel.getValue();

        try {
            salary = Double.parseDouble(salaryLabel.getText().trim());
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "В поле \"зарплата\" ожидалось число", ButtonType.OK);
            alert.show();
            return;
        }

        if (salary < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "В поле \"зарплата\" ожидалось число больше 0", ButtonType.OK);
            alert.show();
            return;
        }

        if (login.isBlank() || pass.isEmpty() || name.length != 3 || email.isBlank() ||
                phoneNumber.isBlank() || roleId == 0 || post.isBlank() || birthday == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены!", ButtonType.OK);
            alert.show();
            return;
        }

        if (Period.between(birthday, LocalDate.now()).getYears() < 18) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Только совершеннолетние пользователи могут зарегистрироваться!", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            List<Employee> employees = employeeService.getEmployeesInfo();
            if (employees.stream().anyMatch(item -> item.getUserLogin().equals(login))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Логин занят. Используйте другой!", ButtonType.OK);
                alert.show();
                return;
            }

            if (employees.stream().anyMatch(item -> item.getEmail().equals(email)) ||
                    employees.stream().anyMatch(item -> item.getPhoneNumber().equals(phoneNumber))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Почта или телефон уже используются в системе!", ButtonType.OK);
                alert.show();
            }

            Employee employee = new Employee(login, pass, email, phoneNumber, roleId, true,
                    name[1], name[0], name[2], post, salary, birthday);

            employeeService.registerEmployee(employee);

            adminController.onEmployeesButtonClick();
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void setInfo(AdminController controller) {
        adminController = controller;
    }
}
