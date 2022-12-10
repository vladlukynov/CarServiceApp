package com.example.app.view.controllers.admin.services;

import com.example.app.entity.Service;
import com.example.app.utils.UIActions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceDescriptionController {
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
        UIActions.getStage(nameLabel).close();
    }
}
