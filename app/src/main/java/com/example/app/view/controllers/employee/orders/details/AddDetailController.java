package com.example.app.view.controllers.employee.orders.details;

import com.example.app.entity.Detail;
import com.example.app.entity.Order;
import com.example.app.service.DetailService;
import com.example.app.service.OrderService;
import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.List;

public class AddDetailController {
    private Order order;
    private DetailsInOrderController detailsInOrderController;
    private final DetailService detailService = new DetailService();
    private final OrderService orderService = new OrderService();
    @FXML
    private ComboBox<Detail> detailLabel;
    @FXML
    private TextField quantityLabel;

    @FXML
    public void initialize() {
        try {
            List<Detail> details = detailService.getDetails();
            detailLabel.setConverter(new StringConverter<>() {
                @Override
                public String toString(Detail detail) {
                    return detail.getDetailName();
                }

                @Override
                public Detail fromString(String s) {
                    return null;
                }
            });

            for (Detail detail : details) {
                detailLabel.getItems().add(detail);
            }
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onApplyButtonClick() {
        if (detailLabel.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.INFORMATION, "Укажите деталь", ButtonType.OK).show();
            return;
        }

        int detailId = detailLabel.getSelectionModel().getSelectedItem().getDetailId();
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
            orderService.addDetailToOrder(order.getOrderId(), detailId, quantity);
            detailsInOrderController.setInfo(order);
            UIActions.getStage(detailLabel).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCancelButtonClick() {
        UIActions.getStage(detailLabel).close();
    }

    public void setInfo(Order order, DetailsInOrderController controller) {
        detailsInOrderController = controller;
        this.order = order;
    }
}
