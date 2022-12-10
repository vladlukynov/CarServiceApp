package com.example.app.view.controllers.employee.orders;

import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class OrderStatusEnterController {
    private OrderBlockInterface orderBlockController;
    @FXML
    private TextField statusLabel;

    @FXML
    public void onApplyButtonClick() {
        String status = statusLabel.getText().trim();
        if (status.isBlank()) {
            new Alert(Alert.AlertType.INFORMATION, "Введите новый статус услуги").show();
            return;
        }

        orderBlockController.processStartClick(status);
        UIActions.getStage(statusLabel).close();
    }

    @FXML
    public void onCancelButtonClick() {
        UIActions.getStage(statusLabel).close();
    }

    public void setInfo(OrderBlockInterface controller) {
        this.orderBlockController = controller;
    }
}
