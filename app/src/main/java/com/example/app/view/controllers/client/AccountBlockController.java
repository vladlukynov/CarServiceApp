package com.example.app.view.controllers.client;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AccountBlockController {
    private ClientController clientController;
    @FXML
    private Label nameLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;

    @FXML
    public void onEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/account-edit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AccountEditController controller = fxmlLoader.getController();
            controller.setInfo(clientController);

            Stage stage = new Stage();
            stage.setTitle("Редактирование");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void initialize() {
        Client client = (Client) CarServiceApplication.getUser();

        nameLabel.setText("ФИО: " + client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());
        loginLabel.setText("Логин: " + client.getUserLogin());
        birthdayLabel.setText("Дата рождения: " + client.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        phoneLabel.setText("Номер телефона: " + client.getPhoneNumber());
        emailLabel.setText("Электронная почта: " + client.getEmail());
    }

    public void setInfo(ClientController controller) {
        this.clientController = controller;
    }
}
