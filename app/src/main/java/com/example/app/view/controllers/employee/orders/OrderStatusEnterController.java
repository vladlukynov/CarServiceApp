package com.example.app.view.controllers.employee.orders;

import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class OrderStatusEnterController {
    private OrderBlockController orderBlockController;
    @FXML
    private TextField statusLabel;

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        String status = statusLabel.getText().trim();
        if (status.isBlank()) {
            new Alert(Alert.AlertType.INFORMATION, "Введите новый статус услуги").show();
            return;
        }

        orderBlockController.processStartClick(status);
        UIActions.getStage(event).close();
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    public void setInfo(OrderBlockController controller) {
        this.orderBlockController = controller;
    }
}
