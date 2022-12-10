package com.example.app.utils;

import com.example.app.CarServiceApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIActions {
    public static Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

    public static void changeScene(String view, String title,
                                   Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource(view));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(title);
        stage.setScene(scene);
    }

    public static <T> T createStage(String view, String title, Stage oldStage, boolean closeCurrentStage) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(CarServiceApplication.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setTitle(title);
        newStage.setResizable(false);
        newStage.setScene(scene);

        if (closeCurrentStage) {
            oldStage.close();
        }

        newStage.show();
        return fxmlLoader.getController();
    }
}
