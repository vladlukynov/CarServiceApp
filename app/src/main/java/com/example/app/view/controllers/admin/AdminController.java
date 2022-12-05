package com.example.app.view.controllers.admin;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Car;
import com.example.app.entity.Employee;
import com.example.app.service.CarService;
import com.example.app.service.EmployeeService;
import com.example.app.view.controllers.admin.cars.CarAddController;
import com.example.app.view.controllers.admin.cars.CarBlockController;
import com.example.app.view.controllers.admin.employees.EmployeeAddController;
import com.example.app.view.controllers.admin.employees.EmployeesBlockController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final EmployeeService employeeService = new EmployeeService();
    private final CarService carService = new CarService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private VBox primaryLayout;

    @FXML
    void initialize() {
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
                employeesBlockController.setInfo(employee);

                primaryLayout.getChildren().add(node);
            }
        } catch (SQLException | IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void onEmployeeAddButtonClick() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/employees/add-employee-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            EmployeeAddController controller = fxmlLoader.getController();
            controller.setInfo(this);
            stage.setScene(scene);
            stage.setTitle("Регистрация сотрудника");
            stage.setResizable(false);

            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void onAddCarButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/cars/car-add-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            CarAddController carAddController = fxmlLoader.getController();
            carAddController.setInfo(this);
            Stage stage = new Stage();
            stage.setTitle("Добавление автомобиля");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onServicesButtonClick() {
        System.out.println("Services");
    }

    @FXML
    public void onDetailsButtonClick() {
        System.out.println("Details");
    }

    @FXML
    public void onOrdersButtonClick() {
        System.out.println("Orders");
    }
}
