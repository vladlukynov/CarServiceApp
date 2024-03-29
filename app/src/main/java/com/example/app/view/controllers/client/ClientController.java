package com.example.app.view.controllers.client;

import com.example.app.CarServiceApplication;
import com.example.app.entity.*;
import com.example.app.service.CarService;
import com.example.app.service.DetailService;
import com.example.app.service.OrderService;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.client.cars.CarBlockController;
import com.example.app.view.controllers.client.details.DetailsBlockController;
import com.example.app.view.controllers.client.orders.OrderBlockController;
import com.example.app.view.controllers.client.services.ServiceBlockController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientController {
    private final ServiceService serviceService = new ServiceService();
    private final DetailService detailService = new DetailService();
    private final OrderService orderService = new OrderService();
    private final CarService carService = new CarService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private VBox primaryLayout;

    @FXML
    public void initialize() {
        Client client = (Client) CarServiceApplication.getUser();
        nameLabel.setText(client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName());
        userNameLabel.setText(client.getUserLogin());
    }

    @FXML
    public void onServicesButtonClick() {
        try {
            List<Service> services = serviceService.getServices().stream().filter(Service::isActive).toList();

            primaryLayout.getChildren().clear();
            for (Service service : services) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/services/service-block-view.fxml"));
                Node node = fxmlLoader.load();
                ServiceBlockController controller = fxmlLoader.getController();
                controller.setInfo(service);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onDetailsButtonClick() {
        try {
            List<Detail> details = detailService.getDetails();

            primaryLayout.getChildren().clear();
            for (Detail detail : details) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/details/details-block-view.fxml"));
                Node node = fxmlLoader.load();
                DetailsBlockController controller = fxmlLoader.getController();
                controller.setInfo(detail);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onOrdersButtonClick() {
        try {
            List<Order> orders = orderService.getClientOrders(CarServiceApplication.getUser().getUserLogin());

            primaryLayout.getChildren().clear();
            for (Order order : orders) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/orders/order-block-view.fxml"));
                Node node = fxmlLoader.load();
                OrderBlockController controller = fxmlLoader.getController();
                controller.setInfo(order);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void addOrderButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/add-order-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Запись на обслуживание");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCarsButtonClick() {
        try {
            List<Car> cars = carService.getClientCars(CarServiceApplication.getUser().getUserLogin());

            primaryLayout.getChildren().clear();
            for (Car car : cars) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/cars/car-block-view.fxml"));
                Node node = fxmlLoader.load();
                CarBlockController controller = fxmlLoader.getController();
                controller.setInfo(car);
                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onAccountButtonClick() {
        primaryLayout.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("client/account-block-view.fxml"));
            Node node = fxmlLoader.load();
            AccountBlockController controller = fxmlLoader.getController();
            controller.setInfo(this);
            primaryLayout.getChildren().add(node);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void exitButtonClick() {
        try {
            CarServiceApplication.setUser(null);
            UIActions.createStage("auth/auth-view.fxml", "Авторизация", UIActions.getStage(nameLabel), true);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
