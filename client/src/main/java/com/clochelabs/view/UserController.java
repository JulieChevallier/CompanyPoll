package com.clochelabs.view;
/**
 * {@link UserController} controls the login view.
 */

import com.clochelabs.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class UserController {
    @FXML
    private BorderPane root;
    // TODO: Implement this class.




    @FXML
    private void initialize(){
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null,null)));
        if(User.getInstance().isAdmin()){
            root.setLeft(new ViewSideAdmin());
        }else{
            root.setLeft(new ViewSide());
        }
        root.setCenter(new WelcomeMenu());
        Client.setMainView(this);
    }

    public void setCenter(Node node){
        root.setCenter(node);
    }

    
}
