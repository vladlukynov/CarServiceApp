package com.example.app.view.controllers.employee.services;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceBlockController {
    private Service service;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;

    @FXML
    public void onDescButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/services/service-description-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ServiceDescriptionController controller = fxmlLoader.getController();
            controller.setInfo(service);
            Stage stage = new Stage();
            stage.setTitle(service.getServiceName());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(Service service) {
        this.service = service;

        nameLabel.setText(service.getServiceName());
        priceLabel.setText("Стоимость: " + service.getPrice() + " руб.");
    }
}
