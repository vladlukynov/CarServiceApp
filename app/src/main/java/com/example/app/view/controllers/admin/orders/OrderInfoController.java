package com.example.app.view.controllers.admin.orders;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Order;
import com.example.app.entity.OrderElement;
import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderInfoController {
    @FXML
    private Label titleLabel;
    @FXML
    private VBox primaryLayout;

    public void setInfo(Order order) {
        try {
            titleLabel.setText("Прайс-лист заказа № " + order.getOrderId());
            for (OrderElement element : order.getElements()) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/orders/order-info-block-view.fxml"));
                Node node = fxmlLoader.load();
                OrderInfoBlockController controller = fxmlLoader.getController();
                controller.setInfo(element);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCloseButtonClick() {
        UIActions.getStage(titleLabel).close();
    }
}
