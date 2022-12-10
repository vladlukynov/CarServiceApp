package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ServiceDescriptionController {
    private final Stage currentStage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
    @FXML
    private Label nameLabel;
    @FXML
    private Label descLabel;

    public void setInfo(Service service) {
        nameLabel.setText(service.getServiceName());
        descLabel.setText(service.getDescription());
    }

    @FXML
    public void onCloseButtonClick() {
        currentStage.close();
    }
}
