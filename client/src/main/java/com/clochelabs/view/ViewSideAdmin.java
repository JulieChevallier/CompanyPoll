package com.clochelabs.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;

public class ViewSideAdmin extends ViewSide {

    @FXML
    private HBox addPoll;

    @FXML
    private HBox addEmployee;

    @FXML
    private HBox deleteEmployee;

    public ViewSideAdmin(){
        super();

    }
    @Override
    protected void initializeLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewSideAdmin.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void createBindings(){

        super.createBindings();

        addPoll.setOnMouseClicked(e->{
            Client.setCenter(new AddPollMenu());
        });

        addEmployee.setOnMouseClicked(e->{
            Client.setCenter(new AddEmployeeMenu());
        });

        deleteEmployee.setOnMouseClicked(e-> Client.setCenter(new RemoveEmployeeMenu()));
    }



}
