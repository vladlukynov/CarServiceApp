package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import javafx.scene.control.*;

public class ServiceUtils {
    public static Service getServiceWithInputCheck(TextField nameLabel, TextArea descLabel, TextField priceLabel) {
        String name = nameLabel.getText().trim();
        String desc = descLabel.getText().trim();
        double price;
        try {
            price = Double.parseDouble(priceLabel.getText().trim());
        } catch (NumberFormatException exception) {
            new Alert(Alert.AlertType.INFORMATION, "Поле стоимость должно быть числом", ButtonType.OK).show();
            return null;
        }

        if (name.isBlank() || desc.isBlank()) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены", ButtonType.OK).show();
            return null;
        }

        if (price < 0) {
            new Alert(Alert.AlertType.INFORMATION, "Стоимость не может быть меньше 0", ButtonType.OK).show();
            return null;
        }

        return new Service(name, desc, price);
    }
}
