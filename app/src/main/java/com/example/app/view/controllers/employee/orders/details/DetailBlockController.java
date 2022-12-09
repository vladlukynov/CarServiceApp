package com.example.app.view.controllers.employee.orders.details;

import com.example.app.entity.Detail;
import com.example.app.entity.Order;
import com.example.app.service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class DetailBlockController {
    private final OrderService orderService = new OrderService();
    private DetailsInOrderController detailsInOrderController;
    private Detail detail;
    private Order order;
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;

    public void setInfo(Detail detail, Order order, DetailsInOrderController controller) {
        detailsInOrderController = controller;
        this.order = order;
        this.detail = detail;
        try {
            int quantity = orderService.getDetailQuantityInOrder(order.getOrderId(), detail.getDetailId());

            nameLabel.setText(detail.getDetailName());
            quantityLabel.setText("Количество в заказе: " + quantity);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        try {
            orderService.deleteDetailFromOrder(order.getOrderId(), detail.getDetailId());
            detailsInOrderController.setInfo(order);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onPlusButtonClick() {
        try {
            orderService.addDetailToOrder(order.getOrderId(), detail.getDetailId(), 1);
            detailsInOrderController.setInfo(order);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
