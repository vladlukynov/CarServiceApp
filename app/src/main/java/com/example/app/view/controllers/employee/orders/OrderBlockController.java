package com.example.app.view.controllers.employee.orders;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Car;
import com.example.app.entity.Client;
import com.example.app.entity.Order;
import com.example.app.exception.NoCarByIdException;
import com.example.app.exception.NoClientByLoginException;
import com.example.app.service.CarService;
import com.example.app.service.ClientService;
import com.example.app.service.OrderService;
import com.example.app.view.controllers.employee.EmployeeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class OrderBlockController {
    private Order order;
    private EmployeeController employeeController;
    private final CarService carService = new CarService();
    private final ClientService clientService = new ClientService();
    private final OrderService orderService = new OrderService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label carLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label createLabel;

    public void setInfo(Order order, EmployeeController controller) {
        try {
            this.order = order;
            this.employeeController = controller;
            Car car = carService.getCar(order.getCarId());

            nameLabel.setText("Заказ № " + order.getOrderId());
            carLabel.setText(car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear() + " " + order.getCarNumber());

            try {
                Client client = clientService.getClientInfo(order.getClientLogin());
                clientLabel.setText("Клиент: " + client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());
            } catch (SQLException | NoClientByLoginException exception) {
                new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
                return;
            }

            createLabel.setText("Создан: " + order.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            statusLabel.setText(order.getStatus() + ", изменен: " + order.getStatusChangeDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } catch (SQLException | NoCarByIdException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onStartButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/orders/order-status-enter-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            OrderStatusEnterController controller = fxmlLoader.getController();
            controller.setInfo(this);
            Stage stage = new Stage();
            stage.setTitle("Укажите статус");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void processStartClick(String status) {
        try {
            orderService.addEmployeeToOrder(order.getOrderId(), CarServiceApplication.getUser().getUserLogin(), status);
            employeeController.onOrdersButtonClick();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
