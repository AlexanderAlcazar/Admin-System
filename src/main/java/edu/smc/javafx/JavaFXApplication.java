package edu.smc.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the entry point for JavaFX applications.
 * It extends the Application class and provides the start()
 * method to start the application.
 */
public class JavaFXApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavaFXApplication.class.getResource("initial-view.fxml"));
        Scene scene = new Scene(loader.load(),320, 240);
        stage.setTitle("Final Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}