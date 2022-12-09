package com.example.app.view.controllers.employee.orders.services;

import com.example.app.entity.Order;
import com.example.app.entity.Service;
import com.example.app.service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class ServiceBlockController {
    private final OrderService orderService = new OrderService();
    private ServicesInOrderController serviceInOrderController;
    private Service service;
    private Order order;
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;

    public void setInfo(Service service, Order order, ServicesInOrderController controller) {
        serviceInOrderController = controller;
        this.order = order;
        this.service = service;
        try {
            int quantity = orderService.getServiceQuantityInOrder(order.getOrderId(), service.getServiceId());

            nameLabel.setText(service.getServiceName());
            quantityLabel.setText("Количество в заказе: " + quantity);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        try {
            orderService.deleteServiceFromOrder(service.getServiceId(), order.getOrderId());
            serviceInOrderController.setInfo(order);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onPlusButtonClick() {
        try {
            orderService.addServiceToOrder(service.getServiceId(),order.getOrderId(), 1);
            serviceInOrderController.setInfo(order);
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
