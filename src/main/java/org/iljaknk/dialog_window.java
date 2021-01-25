package org.iljaknk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.iljaknk.App.*;
import static org.iljaknk.app_window_Controller.edit_flag;

public class dialog_window implements Initializable {

    private static Stage dialog_window;
    private static String soldier_id;
    private static String soldier_first_name;
    private static String soldier_last_name;

    public TextField textfield_ID;
    public TextField textfield_first_name;
    public TextField textfield_last_name;
    public TextField textfield_Rank;

    public Button btn_Submit;
    public Button btn_Cancel;

    public void handle_button_Action (ActionEvent event)
    {
        if (event.getSource() == btn_Submit)
        {
            submit_Record();
        }
        else if (event.getSource() == btn_Cancel)
        {
            cancel_adding();
        }
    }

    @FXML
    public static void display(String id, String first_name, String last_name) {

        soldier_id = id;
        soldier_first_name = first_name;
        soldier_last_name = last_name;

        dialog_window = new Stage();
        Scene scene = null;

        try
        {
             scene = new Scene(loadFXML("dialog_window"));
        }
        catch (Exception e)
        {
            System.out.println("Couldn't load a dialog window!");
        }

        dialog_window.initModality(Modality.APPLICATION_MODAL);
        dialog_window.setTitle("Edit Soldier data");
        dialog_window.setScene(scene);
        dialog_window.showAndWait();

    }

    private void submit_Record ()
    {
        if (edit_flag)
        {
            edit_Soldier();
        }

        else
        {
            add_Soldier();
        }

        dialog_window.close();
    }

    private void cancel_adding ()
    {
        dialog_window.close();
    }

    public void add_Soldier ()
    {
        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("insert into list_of_soldiers (id, first_name, last_name) values (?,?,?)");

            stmt.setString(1, textfield_ID.getText());
            stmt.setString(2, textfield_first_name.getText());
            stmt.setString(3, textfield_last_name.getText());

            stmt.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void edit_Soldier ()
    {
        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("update list_of_soldiers set first_name=?, last_name=? where id=?");

            stmt.setString(1, textfield_first_name.getText());
            stmt.setString(2, textfield_last_name.getText());
            stmt.setString(3, textfield_ID.getText());

            stmt.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textfield_ID.setText(soldier_id);
        textfield_first_name.setText(soldier_first_name);
        textfield_last_name.setText(soldier_last_name);

        if (edit_flag)
        {
            btn_Submit.setText("Edit Soldier");
            dialog_window.setTitle("Edit Soldier data");
        }
        else
        {
            btn_Submit.setText("Add Soldier");
            dialog_window.setTitle("Add Soldier");
        }
    }
}
