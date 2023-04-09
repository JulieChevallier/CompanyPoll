package com.clochelabs.view;

import com.clochelabs.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddEmployeeMenu extends AnchorPane{
    
    @FXML
    private TextField mailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private Button addButton;

    public AddEmployeeMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        createBinding();
    }

    private String validField(TextField field){
        try {
            return field.getText();
        }
        catch(Exception e){
            FieldAnim.shakeErrorAnimation(field);
        }
        return null;
    }

    @FXML 
    private void addEmployee(){
        User user=User.getInstance();
        String mail=validField(mailField);
        user.addUser(mail);
        user.setLastName(validField(nameField), mail);
        user.setFirstName(validField(firstNameField), mail);
        mailField.clear();
        firstNameField.clear();
        nameField.clear();
    }

    private void createBinding(){
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addEmployee();
            }
        });
        setMinWidth(Client.getScene().widthProperty().getValue() * 0.7);
        setMaxWidth(Client.getScene().widthProperty().getValue() * 0.6);
    }

}
