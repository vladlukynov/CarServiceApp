package com.example.app.view.controllers.admin.employees;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.service.EmployeeService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class EmployeeEditController {
    private AdminController adminController;
    private Employee employee;
    private EmployeesBlockController employeesBlockController;
    private final EmployeeService employeeService = new EmployeeService();
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

    public void setInfo(Employee employee_, EmployeesBlockController controller, AdminController adminController) {
        employee = employee_;
        employeesBlockController = controller;
        this.adminController = adminController;

        loginLabel.setText(employee_.getUserLogin());
        passLabel.setText(employee_.getPass());
        nameLabel.setText(employee_.getLastName() + " " + employee_.getFirstName() + " " + employee_.getMiddleName());
        emailLabel.setText(employee_.getEmail());
        phoneLabel.setText(employee_.getPhoneNumber());
        roleLabel.getSelectionModel().select(employee_.getRoleId() - 1);
        postLabel.setText(employee_.getPost());
        salaryLabel.setText(Double.toString(employee_.getSalary()));
        birthdayLabel.setValue(employee_.getBirthday());
    }

    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
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
            new Alert(Alert.AlertType.INFORMATION, "В поле \"зарплата\" ожидалось число", ButtonType.OK).show();
            return;
        }

        if (salary < 0) {
            new Alert(Alert.AlertType.INFORMATION, "В поле \"зарплата\" ожидалось число больше 0", ButtonType.OK).show();
            return;
        }

        if (login.isBlank() || pass.isEmpty() || name.length != 3 || email.isBlank() ||
                phoneNumber.isBlank() || post.isBlank() || birthday == null) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены!", ButtonType.OK).show();
            return;
        }

        if (Period.between(birthday, LocalDate.now()).getYears() < 18) {
            new Alert(Alert.AlertType.INFORMATION, "Только совершеннолетние пользователи могут зарегистрироваться!", ButtonType.OK).show();
            return;
        }

        try {
            List<Employee> employees = employeeService.getEmployeesInfo();
            if (employees.stream().anyMatch(item -> item.getUserLogin().equals(login) && !item.getUserLogin().equals(employee.getUserLogin()))) {
                new Alert(Alert.AlertType.INFORMATION, "Логин занят. Используйте другой!", ButtonType.OK).show();
                return;
            }

            if (employees.stream().anyMatch(item -> item.getEmail().equals(email) && !item.getEmail().equals(employee.getEmail())) ||
                    employees.stream().anyMatch(item -> item.getPhoneNumber().equals(phoneNumber) && !item.getPhoneNumber().equals(employee.getPhoneNumber()))) {
                new Alert(Alert.AlertType.INFORMATION, "Почта или телефон уже используются в системе!", ButtonType.OK).show();
            }

            if (!employee.getPass().equals(pass)) {
                pass = DigestUtils.md5Hex(pass);
            }

            Employee newEmployee = new Employee(login, pass, email, phoneNumber, roleId, employee.isActive(),
                    name[1], name[0], name[2], post, salary, birthday);
            employeeService.updateEmployee(employee.getUserLogin(), newEmployee);
            employeesBlockController.setInfo(newEmployee, adminController);

            if (employee.getUserLogin().equals(CarServiceApplication.getUser().getUserLogin())) {
                CarServiceApplication.setUser(newEmployee);
                adminController.initialize();
            }

            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
