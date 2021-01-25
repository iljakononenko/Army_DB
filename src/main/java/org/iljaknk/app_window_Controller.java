package org.iljaknk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import static org.iljaknk.App.*;


public class app_window_Controller {

    public static boolean edit_flag;
    public static boolean delete_flag = false;

    public TextField textfield_ID;
    public TextField textfield_first_name;
    public TextField textfield_last_name;
    public TextField textfield_Rank;
    public TableView<Soldiers> table_view;
    public TableColumn<Soldiers, Integer> column_ID;
    public TableColumn<Soldiers, String> column_first_name;
    public TableColumn<Soldiers, String> column_last_name;
    public TableColumn<Soldiers, String> column_rank_id;
    public Button btn_Search;
    public Button btn_Add;
    public Button btn_Delete;
    public Button btn_Update;
    public Button btn_Clear;

    public String id;
    public String first_name;
    public String last_name;

    ResultSet resultSet = null;

    public void handle_button_Action (ActionEvent event)
    {
        if (event.getSource() == btn_Add)
        {
            add_Record();
        }
        else if (event.getSource() == btn_Delete)
        {
            delete_Record();
        }
        else if (event.getSource() == btn_Update)
        {
            edit_Record();
        }
        else if (event.getSource() == btn_Search)
        {
            find_Records();
        }
        else if (event.getSource() == btn_Clear)
        {
            clear_textfields();
        }
    }

    public void handle_mouse_Action (MouseEvent e)
    {
        Soldiers soldier = table_view.getSelectionModel().getSelectedItem();
        textfield_ID.setText(String.valueOf(soldier.getId()));
        textfield_first_name.setText(soldier.getFirst_name());
        textfield_last_name.setText(soldier.getLast_name());
        textfield_Rank.setText(soldier.getRank());
    }

    public ObservableList<Soldiers> get_list_of_Soldiers (String id, String first_name, String last_name)
    {
        ObservableList<Soldiers> soldiers_list = FXCollections.observableArrayList();

        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("select list_of_soldiers.id, list_of_soldiers.first_name, list_of_soldiers.last_name, list_of_ranks.rank_title " +
            "from list_of_soldiers left join list_of_ranks on list_of_soldiers.rank_id = list_of_ranks.id where list_of_soldiers.id like ? and first_name like ? and last_name " +
            "like ?");

            stmt.setString(1, id);
            stmt.setString(2, first_name);
            stmt.setString(3, last_name);

            resultSet = stmt.executeQuery();
            Soldiers soldier;

            while (resultSet.next())
            {
                soldier = return_soldier("id", "first_name", "last_name", "rank_title");
                soldiers_list.add(soldier);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return soldiers_list;
    }

    public Soldiers return_soldier (String col_1, String col_2, String col_3, String col_4) throws SQLException
    {
        return new Soldiers(resultSet.getInt(col_1), resultSet.getString(col_2), resultSet.getString(col_3), resultSet.getString(col_4));
    }

    public void find_Records()
    {
        get_data_from_textfields();
        show_Soldiers(id + "%", first_name + "%", last_name + "%");
    }

    public void show_Soldiers (String id, String first_name, String last_name)
    {
        ObservableList<Soldiers> soldier_list = get_list_of_Soldiers(id, first_name, last_name);

        column_ID.setCellValueFactory(new PropertyValueFactory<Soldiers, Integer>("id"));
        column_first_name.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("first_name"));
        column_last_name.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("last_name"));
        column_rank_id.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("rank"));

        table_view.setItems(soldier_list);
    }

    private void add_Record()
    {
        edit_flag = false;
        get_data_from_textfields();
        id = String.valueOf(get_last_id());
        dialog_window.display(id, first_name, last_name);
        show_all_Soldiers();
        clear_textfields();
    }

    private void edit_Record()
    {
        edit_flag = true;
        get_data_from_textfields();
        dialog_window.display(id, first_name, last_name);
        show_all_Soldiers();
        clear_textfields();
    }

    private void delete_Record ()
    {
        get_data_from_textfields();
        delete_confirmation.display(id, first_name, last_name);
        show_all_Soldiers();
        clear_textfields();
    }

    private void clear_textfields ()
    {
        textfield_ID.setText("");
        textfield_first_name.setText("");
        textfield_last_name.setText("");
        textfield_Rank.setText("");
    }

    private void show_all_Soldiers ()
    {
        show_Soldiers("%", "%", "%");
    }

    private void get_data_from_textfields ()
    {
        id = textfield_ID.getText();
        first_name = textfield_first_name.getText();
        last_name = textfield_last_name.getText();
    }

    private int get_last_id ()
    {
        int new_id = 0;
        try
        {
            String query = "select id from list_of_soldiers order by id desc limit 1";
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                new_id = rs.getInt("id");
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return new_id + 1;
    }

}