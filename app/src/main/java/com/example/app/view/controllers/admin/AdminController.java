package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.*;
import com.example.app.service.*;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.admin.cars.CarAddController;
import com.example.app.view.controllers.admin.cars.CarBlockController;
import com.example.app.view.controllers.admin.details.DetailsAddController;
import com.example.app.view.controllers.admin.details.DetailsBlockController;
import com.example.app.view.controllers.admin.employees.EmployeeAddController;
import com.example.app.view.controllers.admin.employees.EmployeesBlockController;
import com.example.app.view.controllers.admin.orders.OrderBlockController;
import com.example.app.view.controllers.admin.services.ServiceAddController;
import com.example.app.view.controllers.admin.services.ServiceBlockController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final EmployeeService employeeService = new EmployeeService();
    private final CarService carService = new CarService();
    private final ServiceService serviceService = new ServiceService();
    private final DetailService detailService = new DetailService();
    private final OrderService orderService = new OrderService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private VBox primaryLayout;

    @FXML
    public void initialize() {
        Employee employee = (Employee) CarServiceApplication.getUser();

        nameLabel.setText(employee.getLastName() +
                " " + employee.getFirstName() +
                " " + employee.getMiddleName());
        userNameLabel.setText(CarServiceApplication.getUser().getUserLogin());
    }

    @FXML
    public void onEmployeesButtonClick() {
        try {
            List<Employee> employees = employeeService.getEmployeesInfo();
            employees.sort((o1, o2) -> o1.getLastName().compareToIgnoreCase(o2.getLastName()));

            Button addButton = new Button();
            addButton.setText("Регистрация сотрудника");
            addButton.setOnAction(event -> onEmployeeAddButtonClick());
            primaryLayout.getChildren().clear();
            primaryLayout.getChildren().add(addButton);
            for (Employee employee : employees) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employees/employees-block-view.fxml"));
                Node node = fxmlLoader.load();
                EmployeesBlockController employeesBlockController = fxmlLoader.getController();
                employeesBlockController.setInfo(employee, this);

                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onEmployeeAddButtonClick() {
        try {
            EmployeeAddController controller = UIActions.createStage("admin/employees/add-employee-view.fxml", "Регистрация сотрудника",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(this);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onCarsButtonClick() {
        try {
            List<Car> cars = carService.getCars();

            Button addButton = new Button();
            addButton.setText("Добавить автомобиль");
            addButton.setOnAction(event -> onAddCarButtonClick());

            primaryLayout.getChildren().clear();
            primaryLayout.getChildren().add(addButton);
            for (Car car : cars) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/cars/car-block-view.fxml"));
                Node node = fxmlLoader.load();
                CarBlockController controller = fxmlLoader.getController();
                controller.setInfo(car, this);

                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void onAddCarButtonClick() {
        try {
            CarAddController carAddController = UIActions.createStage("admin/cars/car-add-view.fxml", "Добавление автомобиля",
                    UIActions.getStage(nameLabel), false);
            carAddController.setInfo(this);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onServicesButtonClick() {
        try {
            List<Service> services = serviceService.getServices();

            Button addButton = new Button();
            addButton.setText("Добавить услугу");
            addButton.setOnAction(event -> onServiceAddButtonClick());
            primaryLayout.getChildren().clear();
            primaryLayout.getChildren().add(addButton);
            for (Service service : services) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/services/service-block-view.fxml"));
                Node node = fxmlLoader.load();
                ServiceBlockController controller = fxmlLoader.getController();
                controller.setInfo(service, this);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void onServiceAddButtonClick() {
        try {
            ServiceAddController controller = UIActions.createStage("admin/services/service-add-view.fxml", "Добавить услугу",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(this);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onDetailsButtonClick() {
        try {
            List<Detail> details = detailService.getDetails();
            Button addButton = new Button();
            addButton.setText("Добавить деталь");
            addButton.setOnAction(event -> onDetailsAddButtonClick());

            primaryLayout.getChildren().clear();
            primaryLayout.getChildren().add(addButton);
            for (Detail detail : details) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/details/details-block-view.fxml"));
                Node node = fxmlLoader.load();
                DetailsBlockController controller = fxmlLoader.getController();
                controller.setInfo(detail, this);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void onDetailsAddButtonClick() {
        try {
            DetailsAddController controller = UIActions.createStage("admin/details/details-add-view.fxml", "Добавить деталь",
                    UIActions.getStage(nameLabel), false);
            controller.setInfo(this);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onOrdersButtonClick() {
        try {
            List<Order> orders = orderService.getOrders();

            primaryLayout.getChildren().clear();
            for (Order order : orders) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/orders/order-block-view.fxml"));
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
    public void onAccountButtonClick() {
        primaryLayout.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/account-block-view.fxml"));
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
