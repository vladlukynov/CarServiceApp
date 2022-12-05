package com.example.app.view.controllers.client.details;

import com.example.app.entity.Detail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsBlockController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    public void setInfo(Detail detail) {

        nameLabel.setText(detail.getDetailName());
        priceLabel.setText("Стоимость: " + detail.getPrice());
        quantityLabel.setText("Количество: " + detail.getQuantity());
    }
}
