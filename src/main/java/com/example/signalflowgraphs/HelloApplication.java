package com.example.signalflowgraphs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 700);
        stage.setTitle("Signal Flow Graphs Solver!");

        // For Image as an icon
        Image icon = new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("logo.png")));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Launch 🙂️
        launch();
    }
}