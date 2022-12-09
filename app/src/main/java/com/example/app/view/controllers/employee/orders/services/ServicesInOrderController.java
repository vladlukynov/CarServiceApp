package com.example.app.view.controllers.employee.orders.services;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Order;
import com.example.app.entity.Service;
import com.example.app.exception.NoServiceByIdException;
import com.example.app.service.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ServicesInOrderController {
    private final OrderService orderService = new OrderService();
    private Order order;
    @FXML
    private VBox primaryLayout;

    @FXML
    public void addServiceButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/orders/services/add-service-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddServiceController controller = fxmlLoader.getController();
            controller.setInfo(order, this);
            Stage stage = new Stage();
            stage.setTitle("Добавить услугу");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(Order order) {
        this.order = order;

        primaryLayout.getChildren().clear();
        try {
            List<Service> services = orderService.getOrdersServices(order.getOrderId());
            for (Service service : services) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/orders/services/service-block-view.fxml"));
                Node node = fxmlLoader.load();
                ServiceBlockController controller = fxmlLoader.getController();
                controller.setInfo(service, order, this);
                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | NoServiceByIdException | IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
