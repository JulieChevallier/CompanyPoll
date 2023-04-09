package com.clochelabs.view;

import com.clochelabs.DateUtils;
import com.clochelabs.Scrutin;
import com.clochelabs.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import java.io.IOException;

public class PollMenu extends AnchorPane {

    @FXML
    private Label choiceLabel1;
    @FXML
    private Label choiceLabel2;
    @FXML
    private Button choiceButton1;
    @FXML
    private Button choiceButton2;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label stateLabel;

    private User user;

    @FXML
    private Line separator;
    private ObjectProperty<Scrutin> scrutin=new SimpleObjectProperty<>();

    public PollMenu(Scrutin scrutin) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pollMenu.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        this.user = User.getInstance();
        this.scrutin.set(scrutin);
    }

    @FXML
    private void initialize(){
        createBindings();
        choiceButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vote(0);
            }
        });
        choiceButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vote(1);
            }
        });
    }

    private void vote(int choice){
        if(user.vote(scrutin.get().getId(),choice)){
            stateLabel.setText("Vote enregistré");
        }
        else{
            stateLabel.setText("Vous avez déjà voté");
        }
    }

    private void createBindings(){
        ChangeListener<Scrutin> change=new ChangeListener<Scrutin>() {
            @Override
            public void changed(ObservableValue<? extends Scrutin> observableValue, Scrutin scrutin, Scrutin t1) {
                choiceLabel1.setText(t1.getOption1());
                choiceLabel2.setText(t1.getOption2());
                titleLabel.setText(t1.getTopic());
                dateLabel.setText("Du "+ DateUtils.dateToString(t1.getDateBegin())+" au "+DateUtils.dateToString(t1.getDateEnd()));
            }
        };
        scrutin.addListener(change);

        this.prefHeightProperty().setValue(Client.getScene().getHeight());
        this.prefHeightProperty().setValue(Client.getScene().getHeight());
        separator.endYProperty().setValue((Client.getScene().getHeight()-separator.startYProperty().get())*0.5);
        Client.getScene().heightProperty().addListener(e->{
            this.prefHeightProperty().setValue(Client.getScene().getHeight());
            this.prefHeightProperty().setValue(Client.getScene().getHeight());
            separator.endYProperty().setValue((Client.getScene().getHeight()-separator.startYProperty().get())*0.5);
        });


    }

}
