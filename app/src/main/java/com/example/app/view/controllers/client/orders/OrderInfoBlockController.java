package com.example.app.view.controllers.client.orders;

import com.example.app.entity.OrderElement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderInfoBlockController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    public void setInfo(OrderElement element) {
        nameLabel.setText(element.getElement());
        priceLabel.setText("Стоимость: " + element.getElementSum() + " руб.");
        quantityLabel.setText("Количество: " + element.getQuantity());
    }
}
