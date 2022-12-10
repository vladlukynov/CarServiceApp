package com.example.app.view.controllers.auth;

import com.example.app.CarServiceApplication;
import com.example.app.entity.User;
import com.example.app.exception.NoUserByLoginException;
import com.example.app.service.UserService;

import static com.example.app.utils.UIActions.*;

import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.SQLException;

public class AuthController {
    private final UserService userService = new UserService();
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;

    @FXML
    protected void onAuthButtonClick() {
        String login = loginField.getText().trim();
        String pass = passField.getText();

        if (login.isBlank() || pass.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены!", ButtonType.OK).show();
            return;
        }

        try {
            User user = userService.getUser(login);
            if (!DigestUtils.md5Hex(pass).equals(user.getPass())) {
                new Alert(Alert.AlertType.ERROR, "Введен не верный пароль!", ButtonType.OK).show();
                return;
            }

            if (!user.isActive()) {
                new Alert(Alert.AlertType.ERROR, "Аккаунт не активен в системе!", ButtonType.OK).show();
                return;
            }

            CarServiceApplication.setUser(user);

            switch (user.getRoleId()) {
                case 1 -> createStage("admin/admin-view.fxml", "Администратор", UIActions.getStage(loginField), true);
                case 2 -> createStage("employee/employee-view.fxml", "Сотрудник", UIActions.getStage(loginField), true);
                case 3 -> createStage("client/client-view.fxml", "Клиент", UIActions.getStage(loginField), true);
            }

        } catch (NoUserByLoginException exception) {
            new Alert(Alert.AlertType.ERROR, "Пользователя с таким логином не найдено!", ButtonType.OK).show();
        } catch (SQLException | IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    protected void onRegButtonClick() throws IOException {
        changeScene("auth/register-view.fxml", "Регистрация", UIActions.getStage(loginField));
    }
}
