package com.example.app.view.controllers;

import com.example.app.CarServiceApplication;
import com.example.app.entity.User;
import com.example.app.exception.NoUserByLoginException;
import com.example.app.service.UserService;
import javafx.event.ActionEvent;

import static com.example.app.utils.UIActions.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AuthController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    private final UserService userService = new UserService();

    @FXML
    protected void onAuthButtonClick(ActionEvent event) {
        String login = loginField.getText().trim();
        String pass = passField.getText();

        if (login.isBlank() || pass.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены!", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            User user = userService.getUser(login);
            if (!user.getPass().equals(pass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Введен не верный пароль!", ButtonType.OK);
                alert.show();
                return;
            }

            CarServiceApplication.setUser(user);

            switch (user.getRoleId()) {
                case 1:
                    changeScene("admin/admin-view.fxml", "Главная страница", event);
            }

        } catch (NoUserByLoginException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Пользователя с таким логином не найдено!", ButtonType.OK);
            alert.show();
        } catch (SQLException | IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.show();
        }

    }

    @FXML
    protected void onRegButtonClick(ActionEvent event) throws IOException {
        changeScene("auth/register-view.fxml", "Регистрация", event);
    }
}
