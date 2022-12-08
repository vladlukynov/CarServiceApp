package com.example.app.view.controllers.client.cars;

import com.example.app.entity.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class CarBlockController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label creationDateLabel;

    public void setInfo(Car car) {
        nameLabel.setText(car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear() + " "
                + car.getCarNumber());
        creationDateLabel.setText("Добавлен: " + car.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
