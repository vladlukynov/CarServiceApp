package com.example.app.view.controllers.employee;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Detail;
import com.example.app.entity.Employee;
import com.example.app.entity.Service;
import com.example.app.service.DetailService;
import com.example.app.service.ServiceService;
import com.example.app.utils.UIActions;
import com.example.app.view.controllers.employee.details.DetailsBlockController;
import com.example.app.view.controllers.employee.services.ServiceBlockController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeController {
    private final ServiceService serviceService = new ServiceService();
    private final DetailService detailService = new DetailService();
    @FXML
    private Label nameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private VBox primaryLayout;

    @FXML
    public void initialize() {
        Employee employee = (Employee) CarServiceApplication.getUser();

        nameLabel.setText(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName());
        userNameLabel.setText(employee.getUserLogin());
    }

    @FXML
    public void onServicesButtonClick() {
        try {
            List<Service> services = serviceService.getServices();

            primaryLayout.getChildren().clear();
            for (Service service : services) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/services/service-block-view.fxml"));
                Node node = fxmlLoader.load();
                ServiceBlockController controller = fxmlLoader.getController();
                controller.setInfo(service);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onDetailsButtonClick() {
        try {
            List<Detail> details = detailService.getDetails();

            primaryLayout.getChildren().clear();
            for (Detail detail : details) {
                FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/details/details-block-view.fxml"));
                Node node = fxmlLoader.load();
                DetailsBlockController controller = fxmlLoader.getController();
                controller.setInfo(detail);
                primaryLayout.getChildren().add(node);
            }
        } catch (IOException | SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onOrdersButtonClick() {

    }

    @FXML
    public void onMyOrdersButtonClick() {

    }

    @FXML
    public void onAccountButtonClick() {
        primaryLayout.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("employee/account-block-view.fxml"));
            Node node = fxmlLoader.load();
            AccountBlockController controller = fxmlLoader.getController();
            controller.setInfo(this);
            primaryLayout.getChildren().add(node);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        try {
            CarServiceApplication.setUser(null);
            UIActions.swapStage("auth/auth-view.fxml", "Авторизация", UIActions.getStage(event));
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
