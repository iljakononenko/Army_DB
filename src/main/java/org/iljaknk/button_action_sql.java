package org.iljaknk;

import javafx.scene.control.TextField;
import utilities.Connection_Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class button_action_sql {

    TextField login_field;
    TextField password_field;
    PreparedStatement stmt;
    ResultSet resultSet;

    public void button_action ()
    {
        Connection connection = Connection_Util.connect_to_DB("root", "root");
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
                // if smth went wrong
            }
            else
            {
                // if smth went right
            }

        }
        catch (Exception e)
        {

        }
    }
}
