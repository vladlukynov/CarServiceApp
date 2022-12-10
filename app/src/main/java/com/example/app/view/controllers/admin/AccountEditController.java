package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Employee;
import com.example.app.entity.User;
import com.example.app.service.EmployeeService;
import com.example.app.service.UserService;
import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AccountEditController {
    private final UserService userService = new UserService();
    private final EmployeeService employeeService = new EmployeeService();
    private AdminController adminController;
    private final Stage currentStage = (Stage)Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField loginLabel;
    @FXML
    private PasswordField passLabel;
    @FXML
    private TextField phoneLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private DatePicker birthdayLabel;

    @FXML
    public void initialize() {
        Employee employee = (Employee) CarServiceApplication.getUser();

        nameLabel.setText(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
        loginLabel.setText(employee.getUserLogin());
        passLabel.setText(employee.getPass());
        phoneLabel.setText(employee.getPhoneNumber());
        emailLabel.setText(employee.getEmail());
        birthdayLabel.setValue(employee.getBirthday());
    }

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        String[] name = nameLabel.getText().trim().split(" ");
        String login = loginLabel.getText().trim();
        String pass = passLabel.getText();
        String phone = phoneLabel.getText().trim();
        String email = emailLabel.getText().trim();
        LocalDate birthday = birthdayLabel.getValue();

        if (name.length != 3 || login.isBlank() || pass.isEmpty() || phone.isBlank() ||
                email.isBlank() || birthday == null) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены", ButtonType.OK).show();
            return;
        }

        if (Period.between(birthday, LocalDate.now()).getYears() < 18) {
            new Alert(Alert.AlertType.INFORMATION, "Только совершеннолетние пользователи могут зарегистрироваться!", ButtonType.OK).show();
            return;
        }

        try {
            List<User> users = userService.getUsers();

            if (!CarServiceApplication.getUser().getUserLogin().equals(login) &&
                    users.stream().anyMatch(user -> user.getUserLogin().equals(login))) {
                new Alert(Alert.AlertType.INFORMATION, "Логин уже используется в системе").show();
                return;
            }

            if (!CarServiceApplication.getUser().getEmail().equals(email) &&
                    users.stream().anyMatch(user -> user.getEmail().equals(email))) {
                new Alert(Alert.AlertType.INFORMATION, "Адрес электронной почты уже используется в системе").show();
                return;
            }

            if (!CarServiceApplication.getUser().getPhoneNumber().equals(phone) &&
                    users.stream().anyMatch(user -> user.getPhoneNumber().equals(phone))) {
                new Alert(Alert.AlertType.INFORMATION, "Номер телефона уже используется в системе").show();
                return;
            }

            Employee employee = (Employee) CarServiceApplication.getUser();
            if (!employee.getPass().equals(pass)) {
                pass = DigestUtils.md5Hex(pass);
            }


            Employee newEmployee = new Employee(login, pass, email, phone, employee.getRoleId(),
                    name[1], name[0], name[2], employee.getPost(), employee.getSalary(), birthday);


            employeeService.updateEmployee(employee.getUserLogin(), newEmployee);
            CarServiceApplication.setUser(newEmployee);
            new Alert(Alert.AlertType.INFORMATION, "Учетные данные изменены успешно", ButtonType.OK).show();
            adminController.initialize();
            adminController.onAccountButtonClick();
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCancelButtonClick() {
        currentStage.close();
    }

    public void setInfo(AdminController controller) {
        this.adminController = controller;
    }
}
