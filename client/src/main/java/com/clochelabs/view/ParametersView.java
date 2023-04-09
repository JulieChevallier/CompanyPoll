package com.clochelabs.view;

import com.clochelabs.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ParametersView extends BorderPane {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button changeButton;

    @FXML
    private StackPane backButton;

    public ParametersView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Parameters.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        User user= User.getInstance();
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getMail());
        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(user.setFirstName(firstNameField.getText(),user.getMail()) && user.setLastName(lastNameField.getText(),user.getMail()) && user.setMail(emailField.getText())){
                    Client.setCenter(new WelcomeMenu());
                }
            }
        });
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Client.setCenter(new WelcomeMenu());
            }
        });
    }
}
