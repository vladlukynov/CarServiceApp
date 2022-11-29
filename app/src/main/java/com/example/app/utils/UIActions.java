package com.example.app.utils;

import com.example.app.CarServiceApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIActions {
    public static void changeScene(String view, String title,
                                   ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource(view));

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
    }
}
