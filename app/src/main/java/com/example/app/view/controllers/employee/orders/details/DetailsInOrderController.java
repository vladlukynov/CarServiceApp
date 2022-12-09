package com.example.app.view.controllers.employee.orders.details;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Detail;
import com.example.app.entity.Order;
import com.example.app.exception.NoDetailByIdException;
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

public class DetailsInOrderController {
    private final OrderService orderService = new OrderService();
    private Order order;
    @FXML
    private VBox primaryLayout;

    @FXML
    public void addDetailButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/orders/details/add-detail-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddDetailController controller = fxmlLoader.getController();
            controller.setInfo(order, this);
            Stage stage = new Stage();
            stage.setTitle("Добавить деталь");
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
            List<Detail> details = orderService.getOrderDetails(order.getOrderId());
            for (Detail detail : details) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/orders/details/detail-block-view.fxml"));
                Node node = fxmlLoader.load();
                DetailBlockController controller = fxmlLoader.getController();
                controller.setInfo(detail, order, this);
                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | NoDetailByIdException | IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
