package org.iljaknk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static ChoiceBox<String> table_type;

    public static String user_priviligies = "0";

    public static String global_soldier_id;
    public static String global_squad_id;
    public static boolean edit_flag;
    public static Scene login_scene;
    public static Scene app_scene;
    public static Stage window;
    public static Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        table_type = new ChoiceBox<>();
        table_type.getItems().addAll("Ranks", "Squads", "Hours");

        window = stage;
        login_scene = new Scene(loadFXML("login"));
        window.setScene(login_scene);
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