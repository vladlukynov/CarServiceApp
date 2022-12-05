package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ServiceAddController {
    private AdminController adminController;
    private final ServiceService serviceService = new ServiceService();
    @FXML
    private TextField nameLabel;
    @FXML
    private TextArea descLabel;
    @FXML
    private TextField priceLabel;

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        String name = nameLabel.getText().trim();
        String desc = descLabel.getText().trim();
        double price;
        try {
            price = Double.parseDouble(priceLabel.getText().trim());
        } catch (NumberFormatException exception) {
            new Alert(Alert.AlertType.INFORMATION, "Поле стоимость должно быть числом", ButtonType.OK).show();
            return;
        }

        if (name.isBlank() || desc.isBlank()) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены", ButtonType.OK).show();
            return;
        }

        if (price < 0) {
            new Alert(Alert.AlertType.INFORMATION, "Стоимость не может быть меньше 0", ButtonType.OK).show();
            return;
        }

        try {
            Service service = new Service(name, desc, price);
            serviceService.addService(service);
            adminController.onServicesButtonClick();
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(AdminController controller) {
        adminController = controller;
    }
}
