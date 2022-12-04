package com.example.app.view.controllers.auth;

import com.example.app.entity.Client;
import com.example.app.entity.User;
import com.example.app.service.ClientService;
import com.example.app.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import static com.example.app.utils.UIActions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class RegisterController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker dateField;
    private final UserService userService = new UserService();
    private final ClientService clientService = new ClientService();

    @FXML
    protected void onRegButtonClick(ActionEvent event) {
        String login = loginField.getText().trim();
        String pass = passField.getText();
        String[] name = nameField.getText().trim().split(" ");
        String email = emailField.getText().trim();
        String phoneNumber = phoneField.getText().trim();
        LocalDate birthday = dateField.getValue();

        if (login.isBlank() || pass.isEmpty() || name.length != 3 || email.isBlank() ||
                phoneNumber.isBlank() || birthday == null) {
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
            List<User> users = userService.getUsers();
            if (users.stream().anyMatch(item -> item.getUserLogin().equals(login))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Логин занят. Используйте другой!", ButtonType.OK);
                alert.show();
                return;
            }

            if (users.stream().anyMatch(item -> item.getEmail().equals(email)) ||
                    users.stream().anyMatch(item -> item.getPhoneNumber().equals(phoneNumber))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Почта или телефон уже используются в системе!", ButtonType.OK);
                alert.show();
            }

            Client client = new Client(login, pass, email, phoneNumber, name[1], name[0], name[2], birthday);

            clientService.registrateClient(client);
            changeScene("auth/auth-view.fxml", "Авторизация", event);
        } catch (SQLException | IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) throws IOException {
        changeScene("auth/auth-view.fxml", "Авторизация", event);
    }
}
