package com.clochelabs.view;

import com.clochelabs.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;

public class UserView extends HBox {

    private String[] infos;

    @FXML
    private Label userLabel;

    @FXML
    private ImageView removeButton;

    private RemoveEmployeeMenu removeMenu;

    public UserView(String[] infos, RemoveEmployeeMenu removeMenu) {
        this.infos=infos;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        this.removeMenu = removeMenu;
        userLabel.setText(infos[1] +" "+ infos[2] + " : "+infos[0]);

        removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User.getInstance().removeUser(infos[0]);
                removeMenu.updateUserList();
            }
        });
    }
}
