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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AcceptOrderBlockController implements OrderBlockInterface {
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
    private Label priceLabel;
    @FXML
    private HBox buttons;

    @FXML
    public void onStatusButtonClick() {
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

    @FXML
    public void onDoneButtonClick() {
        try {
            orderService.changeOrderStatus(order.getOrderId(), "Завершен");
            employeeController.onMyOrdersButtonClick();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(Order order, EmployeeController controller, boolean isDelete) {
        this.order = order;
        this.employeeController = controller;

        try {
            Car car = carService.getCar(order.getCarId());
            Client client = clientService.getClientInfo(order.getClientLogin());

            nameLabel.setText("Заказ №" + order.getOrderId());
            carLabel.setText(car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear() + " "
                    + order.getCarNumber());
            clientLabel.setText("Клиент: " + client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());
            statusLabel.setText(order.getStatus() + ", изменен: " + order.getStatusChangeDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            priceLabel.setText("Стоимость: " + order.getTotal());

            if (isDelete) {
                buttons.getChildren().clear();
            }
        } catch (SQLException | NoCarByIdException | NoClientByLoginException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void processStartClick(String status) {
        try {
            orderService.changeOrderStatus(order.getOrderId(), status);
            employeeController.onMyOrdersButtonClick();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
