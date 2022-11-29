package com.example.app.view.controllers;

import javafx.event.ActionEvent;

import static com.example.app.utils.UIActions.*;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;

    @FXML
    protected void onAuthButtonClick() {
        System.out.println("Auth click");
        String login = loginField.getText();
        String pass = passField.getText();
        System.out.println(login);
        System.out.println(pass);
    }

    @FXML
    protected void onRegButtonClick(ActionEvent event) throws IOException {
        changeScene("register-view.fxml", "Регистрация", event);
    }
}
