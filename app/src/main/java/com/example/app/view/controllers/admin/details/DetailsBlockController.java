package com.example.app.view.controllers.admin.details;

import com.example.app.CarServiceApplication;
import com.example.app.entity.Detail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailsBlockController {
    private Detail detail;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    @FXML
    public void onEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource("admin/details/details-edit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DetailsEditController controller = fxmlLoader.getController();
            controller.setInfo(detail, this);
            Stage stage = new Stage();
            stage.setTitle("Редактирование");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK).show();
        }
    }

    public void setInfo(Detail detail) {
        this.detail = detail;

        nameLabel.setText(detail.getDetailName());
        priceLabel.setText("Стоимость: " + detail.getPrice());
        quantityLabel.setText("Количество: " + detail.getQuantity());
    }
}
