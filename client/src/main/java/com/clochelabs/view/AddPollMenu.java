package com.clochelabs.view;

import com.clochelabs.DateUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Date;

public class AddPollMenu extends AnchorPane {

    @FXML
    private TextField titleField;
    @FXML
    private TextField option1Field;
    @FXML
    private TextField option2Field;
    @FXML
    private TextField beginField;
    @FXML
    private TextField endField;
    @FXML
    private Button validateButton;

    public AddPollMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addPollMenu.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        createBindings();
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

    private Date getDateFromTextField(TextField field){

        return DateUtils.stringToDate(validField(field));
    }

    @FXML
    private void addPoll(){
        PollManager.getInstance().add( validField(option1Field),validField(option2Field),validField(titleField),DateUtils.dateToString(getDateFromTextField(endField)) ,DateUtils.dateToString(new Date()));
    }

    private void createBindings(){
        validateButton.setOnMouseClicked(e->{
            addPoll();
        });
        setMinWidth(Client.getScene().widthProperty().getValue() * 0.7);
        setMaxWidth(Client.getScene().widthProperty().getValue() * 0.6);
    }

}