package org.iljaknk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene login;
    public static Scene app_scene;
    public static Stage window;
    public static Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        login = new Scene(loadFXML("login"));
        window.setScene(login);
        window.show();
    }

    static void set_Scene(Scene scene) throws IOException {
        window.setScene(scene);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}