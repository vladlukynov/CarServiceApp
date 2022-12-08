package com.example.app.view.controllers.admin.orders;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Car;
import com.example.app.entity.Client;
import com.example.app.entity.Employee;
import com.example.app.entity.Order;
import com.example.app.exception.NoCarByIdException;
import com.example.app.exception.NoClientByLoginException;
import com.example.app.exception.NoEmployeeByLoginException;
import com.example.app.service.CarService;
import com.example.app.service.ClientService;
import com.example.app.service.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class OrderBlockController {
    private Order order;
    private final CarService carService = new CarService();
    private final ClientService clientService = new ClientService();
    private final EmployeeService employeeService = new EmployeeService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label carLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label employeeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label createLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private VBox infoLayout;

    public void setInfo(Order order) {
        try {
            this.order = order;
            Car car = carService.getCar(order.getCarId());
            Client client = clientService.getClientInfo(order.getClientLogin());

            nameLabel.setText("Заказ № " + order.getOrderId());
            carLabel.setText(car.getManufacturer() + " " + car.getCarModel() + " " + car.getReleaseYear() + " " + order.getCarNumber());
            clientLabel.setText("Клиент: " + client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());

            if (order.getEmployeeLogin() != null) {
                Employee employee = employeeService.getEmployeeInfo(order.getEmployeeLogin());
                employeeLabel.setText("Сотрудник: " + employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
            } else {
                infoLayout.getChildren().remove(employeeLabel);
            }

            createLabel.setText("Создан: " + order.getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            statusLabel.setText(order.getStatus() + ", изменен: " + order.getStatusChangeDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            priceLabel.setText("Стоимость: " + order.getTotal() + " руб.");
        } catch (SQLException | NoCarByIdException | NoClientByLoginException | NoEmployeeByLoginException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onInfoButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/orders/order-info-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            OrderInfoController controller = fxmlLoader.getController();
            controller.setInfo(order);
            Stage stage = new Stage();
            stage.setTitle("Информация");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
