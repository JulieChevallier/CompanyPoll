package com.clochelabs.view;

import com.clochelabs.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class RemoveEmployeeMenu extends BorderPane {
    @FXML
    private VBox employees;

    public static ObservableList<String[]> userList=FXCollections.observableList(User.getInstance().getUserList());

    private static List<String[]> users;

    public RemoveEmployeeMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("removeEmployee.fxml"));
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
        updateUserList();
    }

    public void updateUserList(){
        users = User.getInstance().getUserList();
        employees.getChildren().clear();
        for(String[] infos: users){
            employees.getChildren().add(new UserView(infos, this));
        }
    }



}
