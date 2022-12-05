package com.example.app.view.controllers.admin.cars;

import com.example.app.entity.Car;
import com.example.app.service.CarService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class CarAddController {
    private final CarService carService = new CarService();
    private AdminController adminController;
    @FXML
    private TextField manufacturerLabel;
    @FXML
    private TextField modelLabel;
    @FXML
    private TextField yearLabel;

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        String manufacturer = manufacturerLabel.getText().trim();
        String model = modelLabel.getText().trim();
        int year;
        try {
            year = Integer.parseInt(yearLabel.getText().trim());
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "В поле \"год\" введен не число");
            alert.show();
            return;
        }

        if (manufacturer.isBlank() || model.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Все поля должны быть заполнены!");
            alert.show();
            return;
        }

        if ((year < 1990) || (year > LocalDate.now().getYear())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "В поле год принимается число больше 1990, но не больше текущего года", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            Car car = new Car(manufacturer, model, year, true);
            carService.addCar(car);
            adminController.onCarsButtonClick();
            UIActions.getStage(event).close();
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        UIActions.getStage(event).close();
    }

    public void setInfo(AdminController controller) {
        adminController = controller;
    }
}
