package com.example.app.view.controllers.client;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Car;
import com.example.app.service.CarService;
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

public class AddOrderController {
    private final OrderService orderService = new OrderService();
    private final CarService carService = new CarService();
    @FXML
    private ComboBox<Car> carLabel;
    @FXML
    private TextField carNumberLabel;

    @FXML
    public void initialize() {
        try {
            List<Car> cars = carService.getCars();
            carLabel.setConverter(new StringConverter<>() {
                @Override
                public String toString(Car car) {
                    return car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear();
                }

                @Override
                public Car fromString(String s) {
                    return null;
                }
            });

            for (Car car : cars) {
                if (car.isActive()) {
                    carLabel.getItems().add(car);
                }
            }
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onApplyButtonClick() {
        Car car = carLabel.getSelectionModel().getSelectedItem();
        String carNumber = carNumberLabel.getText().trim();

        if ((car == null) || carNumber.isBlank()) {
            new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены", ButtonType.OK).show();
            return;
        }

        try {
            orderService.addOrder(car.getCarId(), carNumber, CarServiceApplication.getUser().getUserLogin());
            new Alert(Alert.AlertType.INFORMATION, "Автомобиль успешно записан!", ButtonType.OK).show();
            UIActions.getStage(carLabel).close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCancelButtonClick() {
        UIActions.getStage(carLabel).close();
    }
}
