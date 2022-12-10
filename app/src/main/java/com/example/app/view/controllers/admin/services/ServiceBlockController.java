package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import com.example.app.exception.NoServiceByIdException;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.AdminController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class ServiceBlockController {
    private Service service;
    private AdminController adminController;
    private final ServiceService serviceService = new ServiceService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label isActiveLabel;
    @FXML
    private Button isActiveButton;
    @FXML
    private Label priceLabel;

    @FXML
    public void isActiveButtonClick() {
        try {
            if (service.isActive()) {
                isActiveLabel.setText("Услуга не оказыается");
                isActiveButton.setText("Начать оказывать");
                serviceService.deactivateService(service.getServiceId());
            } else {
                isActiveLabel.setText("Услуга оказывается");
                isActiveButton.setText("Перестать оказывать");
                serviceService.activateService(service.getServiceId());
            }
            service = serviceService.getService(service.getServiceId());
        } catch (SQLException | NoServiceByIdException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onDescButtonClick() {
        try {
            ServiceDescriptionController controller = UIActions.createStage("admin/services/service-description-view.fxml", "Описание услуги",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(service);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onEditButtonClick() {
        try {
            ServiceEditController controller = UIActions.createStage("admin/services/service-edit-view.fxml", "Редактирование",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(service, adminController);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(Service service, AdminController controller) {
        this.service = service;
        adminController = controller;

        nameLabel.setText(service.getServiceName());
        isActiveLabel.setText(service.isActive() ? "Услуга оказывается" : "Услуга не оказыается");
        isActiveButton.setText(service.isActive() ? "Перестать оказывать" : "Начать оказывать");
        priceLabel.setText("Стоимость: " + service.getPrice() + " руб.");
    }
}
