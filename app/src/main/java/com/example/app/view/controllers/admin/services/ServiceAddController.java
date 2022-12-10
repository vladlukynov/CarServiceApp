package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.AdminController;
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
    public void onCancelButtonClick() {
        UIActions.getStage(nameLabel).close();
    }

    @FXML
    public void onApplyButtonClick() {
        try {
            Service service = ServiceUtils.getServiceWithInputCheck(nameLabel, descLabel, priceLabel);
            if (service != null) {
                serviceService.addService(service);
                adminController.onServicesButtonClick();
                UIActions.getStage(nameLabel).close();
            }
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(AdminController controller) {
        adminController = controller;
    }
}
