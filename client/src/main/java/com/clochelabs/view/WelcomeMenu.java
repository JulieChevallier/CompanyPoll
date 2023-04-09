package com.clochelabs.view;

import com.clochelabs.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeMenu extends AnchorPane {


    private StringProperty lastName = new SimpleStringProperty();
    @FXML
    private Label usernameLabel;


    public WelcomeMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeMenu.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        User user=User.getInstance();

        lastName.setValue(user.getFirstName()+" "+user.getLastName());

    }

    @FXML
    private void initialize(){
        lastName.addListener(e->{
            usernameLabel.setText(lastName.get());
        });
    }
}
