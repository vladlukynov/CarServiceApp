package com.example.app.utils;

import com.example.app.CarServiceApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIActions {
    public static Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static void changeScene(String view, String title,
                                   ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource(view));

        Stage primaryStage = getStage(event);
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
    }

    public static void swapStage(String view, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
