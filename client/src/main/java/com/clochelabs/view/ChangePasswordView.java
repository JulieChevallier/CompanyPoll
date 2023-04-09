package com.clochelabs.view;

import com.clochelabs.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChangePasswordView extends AnchorPane {

    @FXML
    private PasswordField old;

    @FXML
    private PasswordField newpassword;

    @FXML
    private Button validateButton;

    public ChangePasswordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        createBindings();
    }
    private void createBindings(){
        validateButton.setOnMouseClicked(e->{
            if(User.getInstance().setPassword(old.getText(), newpassword.getText()) ){
                System.out.println(newpassword.getText());
                FieldAnim.shakeSuccessAnimation(validateButton);
            }
            else{
                FieldAnim.shakeErrorAnimation(validateButton);
            }
        });
        /*setMinWidth(Client.getScene().widthProperty().getValue() * 0.7);
        setMaxWidth(Client.getScene().widthProperty().getValue() * 0.6);*/

    }


}
