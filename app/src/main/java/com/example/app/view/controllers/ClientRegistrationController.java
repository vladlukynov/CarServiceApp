package com.example.app.view.controllers;

import static com.example.app.utils.DatabaseConnection.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class ClientRegistrationController {
    @FXML
    private Label textMessage;

    @FXML
    protected void onHelloButtonClick() {
        try {
            TestConnection();
            textMessage.setStyle("-fx-text-fill: green");
            textMessage.setText("Успешное подключение");
        } catch (SQLException e) {
            textMessage.setStyle("-fx-text-fill: red");
            textMessage.setText(e.getMessage());
        }
    }
}
