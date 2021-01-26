package org.iljaknk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static org.iljaknk.App.*;
import static org.iljaknk.App.edit_flag;
import static org.iljaknk.App.global_soldier_id;

public class dialog_window implements Initializable {

    private static Stage dialog_window;
    private static String soldier_rank;
    private static String soldier_first_name;
    private static String soldier_last_name;
    private static String soldier_num_of_hours;

    public TextField textfield_ID;
    public TextField textfield_first_name;
    public TextField textfield_last_name;
    public TextField textfield_num_of_hours;
    public ChoiceBox<String> choicebox_rank;

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
    public static void display() {

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
            stmt = connection.prepareStatement("insert into list_of_soldiers (id, first_name, last_name, duty_hours, rank_id)" +
                    "select ?, ?, ?, ?, id from list_of_ranks where rank_title = ?;");

            stmt.setString(1, textfield_ID.getText());
            stmt.setString(2, textfield_first_name.getText());
            stmt.setString(3, textfield_last_name.getText());
            stmt.setString(4, textfield_num_of_hours.getText());
            stmt.setString(5, choicebox_rank.getValue());

            stmt.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void edit_Soldier ()
    {


        // editing soldier

        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("update list_of_soldiers set first_name=?, last_name=?, duty_hours=?, rank_id = (select id from list_of_ranks" +
                    " where rank_title = ?) where id=?");

            stmt.setString(1, textfield_first_name.getText());
            stmt.setString(2, textfield_last_name.getText());
            stmt.setString(3, textfield_num_of_hours.getText());
            stmt.setString(4, choicebox_rank.getValue());
            stmt.setString(5, textfield_ID.getText());

            stmt.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (edit_flag)
        {
            get_soldier_data();
        }

        get_rank_options();

        textfield_ID.setText(global_soldier_id);
        textfield_first_name.setText(soldier_first_name);
        textfield_last_name.setText(soldier_last_name);
        textfield_num_of_hours.setText(soldier_num_of_hours);

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

    public void get_rank_options ()
    {
        String query = "select rank_title from list_of_ranks";

        try
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                String rank_title = rs.getString("rank_title");
                choicebox_rank.getItems().add(rank_title);
            }
            choicebox_rank.setValue(soldier_rank);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public void get_soldier_data ()
    {
        String query = "select first_name, last_name, duty_hours, rank_title " +
                "from list_of_soldiers left join list_of_ranks " +
                "on list_of_soldiers.rank_id = list_of_ranks.id where list_of_soldiers.id = " + global_soldier_id;

        try
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
            {
                soldier_first_name = rs.getString("first_name");
                soldier_last_name = rs.getString("last_name");
                soldier_num_of_hours = rs.getString("duty_hours");
                soldier_rank = rs.getString("rank_title");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
}
