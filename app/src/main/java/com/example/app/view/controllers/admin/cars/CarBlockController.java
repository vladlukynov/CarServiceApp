package com.example.app.view.controllers.admin.cars;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Car;
import com.example.app.exception.NoCarByIdException;
import com.example.app.service.CarService;
import com.example.app.view.controllers.admin.AdminController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CarBlockController {
    private Car car;
    private final CarService carService = new CarService();
    private AdminController adminController;
    @FXML
    private Label nameLabel;
    @FXML
    private Label isActiveLabel;
    @FXML
    private Button isActiveButton;

    @FXML
    public void onEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/cars/car-edit-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            CarEditController controller = fxmlLoader.getController();
            controller.setInfo(car, adminController);
            stage.setTitle("Редактирование");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    @FXML
    public void isActiveButtonClick() {
        try {
            if (car.isActive()) {
                carService.deactivateCar(car.getCarId());
                isActiveLabel.setText("Автомобили не обслуживаются");
                isActiveButton.setText("Начать обслуживать");
            } else {
                carService.activateCar(car.getCarId());
                isActiveLabel.setText("Автомобили обслуживаются");
                isActiveButton.setText("Перестать обслуживать");
            }
            car = carService.getCar(car.getCarId());
        } catch (SQLException | NoCarByIdException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void setInfo(Car car, AdminController controller) {
        this.car = car;
        adminController = controller;

        nameLabel.setText(car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear());
        isActiveLabel.setText(car.isActive() ? "Автомобили обслуживаются" : "Автомобили не обслуживаются");
        isActiveButton.setText(car.isActive() ? "Перестать обслуживать" : "Начать обслуживать");
    }
}
