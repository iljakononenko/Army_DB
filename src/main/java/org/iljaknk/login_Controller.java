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

    public static String login;
    public static String password;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    public void sign_in_action ()
    {
        login = login_field.getText();
        password = password_field.getText();

        try
        {
            connection = Connection_Util.connect_to_DB("root", "root");
            String sql = "SELECT rank_id FROM list_of_soldiers inner join logins on list_of_soldiers.id = logins.user_id WHERE login = ? AND password = ?";


            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();

            if (login.equals("root") && password.equals("root"))
            {
                wrong_login_data.setVisible(false);
                show_Dialog("Login successful!", null, "Successful");
                log_in_successful();
            }

            if (!resultSet.next())
            {
                wrong_login_data.setVisible(true);
            }
            else
            {
                user_priviligies = resultSet.getString("rank_id");
                //System.out.println(user_priviligies);

                wrong_login_data.setVisible(false);
                show_Dialog("Login successful!", null, "Successful");
                log_in_successful();
            }


        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }


    @FXML
    private void log_in_successful() throws IOException {
        app_scene = new Scene(loadFXML("app_window_hours"));
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
