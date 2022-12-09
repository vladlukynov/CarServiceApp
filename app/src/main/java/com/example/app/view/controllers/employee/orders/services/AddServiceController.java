package com.example.app.view.controllers.employee.orders.services;

import com.example.app.entity.Order;
import com.example.app.entity.Service;
import com.example.app.service.OrderService;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.List;

public class AddServiceController {
    private Order order;
    private ServicesInOrderController serviceInOrderController;
    private final ServiceService serviceService = new ServiceService();
    private final OrderService orderService = new OrderService();
    @FXML
    private ComboBox<Service> serviceLabel;
    @FXML
    private TextField quantityLabel;

    @FXML
    public void initialize() {
        try {
            List<Service> services = serviceService.getServices().stream().filter(Service::isActive).toList();
            serviceLabel.setConverter(new StringConverter<>() {
                @Override
                public String toString(Service service) {
                    return service.getServiceName();
                }

                @Override
                public Service fromString(String s) {
                    return null;
                }
            });

            for (Service service : services) {
                serviceLabel.getItems().add(service);
            }
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        if (serviceLabel.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.INFORMATION, "Укажите услугу", ButtonType.OK).show();
            return;
        }

        int serviceId = serviceLabel.getSelectionModel().getSelectedItem().getServiceId();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityLabel.getText().trim());
        } catch (NumberFormatException exception) {
            new Alert(Alert.AlertType.INFORMATION, "Количество должно быть числом", ButtonType.OK).show();
            return;
        }

        if (quantity < 0) {
            new Alert(Alert.AlertType.INFORMATION, "Количество должно быть больше 0", ButtonType.OK).show();
            return;
        }

        try {
            orderService.addServiceToOrder(serviceId, order.getOrderId(), quantity);
            serviceInOrderController.setInfo(order);
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    public void setInfo(Order order, ServicesInOrderController controller) {
        serviceInOrderController = controller;
        this.order = order;
    }
}
