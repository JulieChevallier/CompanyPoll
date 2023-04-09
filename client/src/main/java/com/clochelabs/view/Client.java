package com.clochelabs.view;

import com.clochelabs.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    private static Scene scene;

    private static Stage stage;

    private static UserController mainView;



    public static Stage getScene() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Client.stage = stage;
        // On load le fichier fxml hello-view (template). Une fois ajouté à la scène, il est affiché et on peut controller les éléments de la vue dans le controller associé (HelloController).
        scene = new Scene(new LoginController());

        stage.setMaximized(true);
        stage.setTitle("CompanyPoll");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.show();

    }

    public static void setCenter(Node node){
        mainView.setCenter(node);
    }

    public static void setMainView(UserController mainView) {
        Client.mainView = mainView;
    }

    /**
     * Disconnect the user if the application is closed.
     */
    @Override
    public void stop(){
        System.out.println("Disconnecting user");
        if(LoginController.user != null) {
            System.out.println(LoginController.user.getMail() + LoginController.user.getPassword());
            LoginController.user.disconnect();
            System.out.println("User disconnected");
        }
        else {
            System.out.println("No user connected");
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setView(Parent parent){
        getScene().getScene().setRoot(parent);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    public static void main(String[] args) {
        launch();
    }
}