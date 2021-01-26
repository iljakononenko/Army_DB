package org.iljaknk;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import static org.iljaknk.App.*;


public class app_window_Controller implements Initializable {

    public TextField textfield_ID;
    public TextField textfield_first_name;
    public TextField textfield_last_name;
    public ChoiceBox<String> choicebox_rank;
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
    public ChoiceBox<String> type_of_table;

    public String id;
    public String first_name;
    public String last_name;
    public static String rank;

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
        choicebox_rank.setValue(soldier.getRank());
    }

    public ObservableList<Soldiers> get_list_of_Soldiers ()
    {
        ObservableList<Soldiers> soldiers_list = FXCollections.observableArrayList();

        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("select list_of_soldiers.id, list_of_soldiers.first_name, list_of_soldiers.last_name, list_of_ranks.rank_title " +
            "from list_of_soldiers left join list_of_ranks on list_of_soldiers.rank_id = list_of_ranks.id where list_of_soldiers.id like ? and first_name like ? and last_name " +
            "like ? and rank_title like ?");

            stmt.setString(1, id + "%");
            stmt.setString(2, first_name + "%");
            stmt.setString(3, last_name + "%");
            stmt.setString(4, rank + "%");

            resultSet = stmt.executeQuery();
            Soldiers soldier;

            while (resultSet.next())
            {
                soldier = return_soldier("id", "first_name", "last_name", "rank_title", "armour", "weapon");
                soldiers_list.add(soldier);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return soldiers_list;
    }

    public Soldiers return_soldier (String col_1, String col_2, String col_3, String col_4, String col_5, String col_6) throws SQLException
    {
        return new Soldiers(resultSet.getInt(col_1), resultSet.getString(col_2), resultSet.getString(col_3), resultSet.getString(col_4), resultSet.getString(col_5), resultSet.getString(col_6));
    }

    public void find_Records()
    {
        get_search_data();
        show_Soldiers();
    }

    public void show_Soldiers ()
    {
        ObservableList<Soldiers> soldier_list = get_list_of_Soldiers();

        column_ID.setCellValueFactory(new PropertyValueFactory<Soldiers, Integer>("id"));
        column_first_name.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("first_name"));
        column_last_name.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("last_name"));
        column_rank_id.setCellValueFactory(new PropertyValueFactory<Soldiers, String>("rank"));

        table_view.setItems(soldier_list);
    }

    private void add_Record()
    {
        edit_flag = false;
        get_search_data();

        global_soldier_id = String.valueOf(get_last_id());
        dialog_window.display();

        end_of_changes();
    }

    private void edit_Record()
    {
        edit_flag = true;
        get_search_data();

        dialog_window.display();

        end_of_changes();
    }

    private void delete_Record ()
    {
        get_search_data();
        delete_confirmation.display(id, first_name, last_name);

        end_of_changes();
    }

    private void end_of_changes()
    {
        clear_Strings();
        clear_textfields();
        show_Soldiers();
    }

    private void clear_textfields ()
    {
        textfield_ID.setText("");
        textfield_first_name.setText("");
        textfield_last_name.setText("");
        choicebox_rank.setValue("");
    }

    private void clear_Strings ()
    {
        id = "";
        first_name = "";
        last_name = "";
        rank = "";
    }

    private void get_search_data()
    {
        global_soldier_id = textfield_ID.getText();
        first_name = textfield_first_name.getText();
        last_name = textfield_last_name.getText();
        rank = choicebox_rank.getValue();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        get_rank_options();
        type_of_table.getItems().addAll(table_type.getItems());
        type_of_table.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> set_table_choicebox(newValue));
    }

    public void set_table_choicebox (String newValue)
    {
        try
        {
            switch (newValue)
            {
                case "Squads":
                    window.setScene(new Scene(loadFXML("app_window_squads")));
                    break;
                case "Hours":
                    window.setScene(new Scene(loadFXML("app_window_hours")));
                    break;
                case "Ranks":
                    window.setScene(new Scene(loadFXML("app_window_main")));
                    break;
            }
        }

        catch (Exception e)
        {

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
            choicebox_rank.setValue("");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
}