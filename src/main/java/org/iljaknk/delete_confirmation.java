package org.iljaknk;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.iljaknk.App.connection;

public class delete_confirmation {

    public static void display (String id, String first_name, String last_name)
    {

        Stage delete_confirmation = new Stage();

        delete_confirmation.initModality(Modality.APPLICATION_MODAL);

        delete_confirmation.setTitle("Delete Confirmation");

        Label label = new Label("Are you sure you want to delete " + first_name + " " + last_name + "?");

        Button button_confirm = new Button("Yes");
        Button button_cancel = new Button("Cancel");

        button_cancel.setOnAction(e ->
        {
            delete_confirmation.close();
        });

        button_confirm.setOnAction(e ->
        {
            delete_soldier(id);
            delete_confirmation.close();
        });

        VBox vBox = new VBox(10);
        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(button_confirm, button_cancel);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(label, hBox);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 400, 200);

        delete_confirmation.setScene(scene);

        delete_confirmation.showAndWait();

    }

    private static void delete_soldier(String soldier_id)
    {
        PreparedStatement stmt = null;

        try
        {
            stmt = connection.prepareStatement("delete from list_of_soldiers where id = ?");

            stmt.setString(1, soldier_id);

            stmt.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}
