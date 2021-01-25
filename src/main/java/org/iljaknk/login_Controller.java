package org.iljaknk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilities.Connection_Util;

import static org.iljaknk.App.*;

public class login_Controller {

    @FXML
    private TextField login_field;

    @FXML
    private Label wrong_login_data;

    @FXML
    private TextField password_field;

    @FXML
    private Button sign_in_button;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    public void sign_in_action ()
    {
        String login = login_field.getText().toString();
        String password = password_field.getText().toString();

        try
        {
            connection = Connection_Util.connect_to_DB("root", "root");
            if (connection == null)
            {
                wrong_login_data.setVisible(true);
            }
            else
            {
                wrong_login_data.setVisible(false);
                show_Dialog("Login successful!", null, "Successful");
                log_in_successful();
            }

        }
        catch (Exception e)
        {

        }
    }

    public void sign_in_action_old_through_table_users ()
    {
        String login = login_field.getText().toString();
        String password = password_field.getText().toString();

        // query example

        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try
        {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();
            if (!resultSet.next())
            {
                wrong_login_data.setVisible(true);
            }
            else
            {
                wrong_login_data.setVisible(false);
                show_Dialog("Login successful!", null, "Successful");
                log_in_successful();
            }

        }
        catch (Exception e)
        {

        }
    }

    @FXML
    private void log_in_successful() throws IOException {
        app_scene = new Scene(loadFXML("app_window"));
        App.set_Scene(app_scene);
    }

    private void show_Dialog (String info, String header, String title)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(info);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
