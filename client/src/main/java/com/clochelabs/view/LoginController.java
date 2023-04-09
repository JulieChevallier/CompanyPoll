package com.clochelabs.view;
/**
 * LoginController controls the login view.
 */

import java.io.IOException;
import java.util.ArrayList;

import com.clochelabs.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController extends VBox {


    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button login;

    @FXML
    VBox container;

    public static User user;


    public LoginController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewLogin.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        createBinding();
    }

    @FXML
    private void onLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        System.out.println("Username: " + username + " Password: " + password);

        // On essaie de se connecter avec les informations entrées par l'utilisateur.
        try  {

            if(login(username, password)) {
                // Si la connexion est réussie, on va switch la vue pour afficher la vue viewUser.
                FieldAnim.shakeSuccessAnimationOnLogin(this.login);
            }
        } catch (Exception e) {
            // Si la connexion échoue, on affiche un message d'erreur.
            FieldAnim.shakeErrorAnimation(this.login);
            e.printStackTrace();
        }
    }



    private boolean login(String username, String password) {
        // TODO: Handle connection to the server.
        System.out.println("Login attempt");
        user = User.getInstance();
        user.setPassword(password);
        user.setMailForLogin(username);
        boolean connectionState = user.connect();

        if(connectionState) {
            System.out.println("Connection successful");
        } else {
            System.out.println("Connection failed");
            FieldAnim.shakeErrorAnimation(login);
            
        }

        return connectionState;
    }

    // initialize
    @FXML
    private void initialize() {
        username.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    try {
                        onLogin();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        });
    
        password.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    try {
                        onLogin();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void createBinding(){


        Client.getScene().heightProperty().addListener(e->{
            container.setPrefHeight(Client.getScene().getHeight());

        });

        Client.getScene().widthProperty().addListener(e->{
            this.prefWidthProperty().setValue(Client.getScene().getWidth());
            username.setPrefWidth(Client.getScene().getWidth()*0.4);
            password.setPrefWidth(Client.getScene().getWidth()*0.4);
        });
    }
}