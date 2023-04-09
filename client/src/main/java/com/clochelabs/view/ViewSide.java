package com.clochelabs.view;

import com.clochelabs.Scrutin;
import com.clochelabs.ScrutinRepository;
import com.clochelabs.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ViewSide extends VBox {
    @FXML
    protected Label usernameContainer;

    @FXML
    protected VBox polls;

    @FXML
    protected VBox root;

    @FXML
    protected HBox signout;

    @FXML
    protected HBox settings;


    @FXML
    protected void initialize(){
        usernameContainer.setText(User.getInstance().getFirstName());
        updatePollList();
    }

    public ViewSide(){

        initializeLoader();
        createBindings();
        PollManager.getInstance().setSide(this);

    }

    protected void initializeLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewSideEmploye.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updatePollList(){
        ArrayList<Scrutin> scrutins = ScrutinRepository.getInstance().getScrutinsAsList();
        System.out.println(scrutins);
        for (Scrutin s : scrutins) {
            if(User.getInstance().isAdmin()){
                polls.getChildren().add(new PollTitleAdmin(s));

            }else{
                polls.getChildren().add(new PollTitle(s));

            }
        }
    }

    public VBox getPolls() {
        return polls;
    }

    protected void createBindings(){
        usernameContainer.setOnMouseClicked(e->{
            Client.setCenter(new ChangePasswordView());
        });
        signout.setOnMouseClicked(e->{
            User.getInstance().disconnect();
            Client.setView(new LoginController());
        });

        settings.setOnMouseClicked(e->{
            Client.setCenter(new ParametersView());
        });

        setPrefWidth(Client.getScene().widthProperty().getValue() * 0.2);
        Client.getScene().widthProperty().addListener(e->{
            setPrefWidth(Client.getScene().widthProperty().getValue() * 0.2);
        });
    }
}
