package com.clochelabs.view;

import com.clochelabs.Scrutin;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Date;

public class PollTitle extends HBox {
    private ObjectProperty<Scrutin> scrutinProperty = new SimpleObjectProperty<>();
    @FXML
    private Label text;
    @FXML
    private ImageView image;




    public PollTitle(Scrutin scrutin, String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        scrutinProperty.set(scrutin);


        text.setPrefWidth((Client.getScene().widthProperty().getValue() * 0.2)-40);

        Client.getScene().widthProperty().addListener(e->{
            text.setPrefWidth((Client.getScene().widthProperty().getValue() * 0.2)-40);
        });
    }

    public PollTitle(Scrutin scrutin){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pollTitle.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        scrutinProperty.set(scrutin);
        setOnMouseClicked(e->{
            Client.setCenter(new PollMenu(scrutin));
        });

        text.setPrefWidth((Client.getScene().widthProperty().getValue() * 0.2)-40);

        Client.getScene().widthProperty().addListener(e->{
            text.setPrefWidth((Client.getScene().widthProperty().getValue() * 0.2)-40);
        });


    }
    @FXML
    private void initialize(){
        setOnMouseEntered(e->{
            setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-cursor: hand;");
        });
        setOnMouseExited(e->{
            setStyle("-fx-background-color: #202026; -fx-cursor: hand;");
        });

        text.setOnMouseClicked(e->{
            if(scrutinProperty.get().isDone()){
                Client.setCenter(new PollResult(scrutinProperty.get()));

            }else{

                Client.setCenter(new PollMenu(scrutinProperty.get()));

            }
        });

        scrutinProperty.addListener((observable, oldValue, newValue)->{
            System.out.println(newValue.getTopic());
            text.setText(newValue.getTopic());
            if(newValue.isDone()){
                image.setImage(new Image(getClass().getResource("images/finished_poll.png").toExternalForm()));
            }else{
                image.setImage(new Image(getClass().getResource("images/not_finished_poll.png").toExternalForm()));
            }
        });

        image.setFitHeight(17);
        image.setFitWidth(17);



    }
}
