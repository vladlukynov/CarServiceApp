package com.example.app.view.controllers.client;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Client;
import com.example.app.entity.User;
import com.example.app.service.ClientService;
import com.example.app.service.UserService;
import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AccountEditController {
    private final UserService userService = new UserService();
    private final ClientService clientService = new ClientService();
    private ClientController clientController;
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
        Client client = (Client) CarServiceApplication.getUser();

        nameLabel.setText(client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());
        loginLabel.setText(client.getUserLogin());
        passLabel.setText(client.getPass());
        phoneLabel.setText(client.getPhoneNumber());
        emailLabel.setText(client.getEmail());
        birthdayLabel.setValue(client.getBirthday());
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
            Client newClient = new Client(login, pass, email, phone, name[1], name[0], name[2], birthday);
            clientService.editClient(CarServiceApplication.getUser().getUserLogin(), newClient);
            CarServiceApplication.setUser(newClient);
            new Alert(Alert.AlertType.INFORMATION, "Учетные данные изменены успешно", ButtonType.OK).show();
            clientController.initialize();
            clientController.onAccountButtonClick();
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    public void setInfo(ClientController controller) {
        this.clientController = controller;
    }
}
