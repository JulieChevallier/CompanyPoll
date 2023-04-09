package com.clochelabs.view;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;

import java.io.IOException;

public class PresetAnimation {

    public static void shakeErrorAnimation(Node node){
        Button button = (Button) node;
        button.setDisable(true);
        node.setEffect(new Glow(0.3));
        node.setStyle("-fx-background-color: red;");
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(javafx.util.Duration.millis(150));
        translateTransition.setNode(node);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            node.setStyle("-fx-background-color: white;");
            node.setEffect(null);
            button.setDisable(false);
        });
    }

    public static void shakeSuccessAnimation(Node node){
        node.setEffect(new Glow(0.3));
        node.setStyle("-fx-background-color: green;");
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(javafx.util.Duration.millis(150));
        translateTransition.setNode(node);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            node.setStyle("-fx-background-color: white;");
            node.setEffect(null);
            try {
                Client.setRoot("viewUser");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}
