package com.clochelabs.view;

import com.clochelabs.Scrutin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;

public class PollResult extends AnchorPane {

    @FXML
    private ProgressBar progress;

    @FXML
    private Label title;

    @FXML
    private Label resultatLabel;

    private ObjectProperty<Scrutin> scrutin = new SimpleObjectProperty<>();

    public PollResult(Scrutin scrutin) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pollResult.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        double resultat = 0.5;
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        title.setText(scrutin.getTopic());
        this.scrutin.set(scrutin);
        scrutin.askForResult();
        System.out.println(scrutin.getNbVotants());
        if (scrutin.getNbVotants() != 0) {
            resultat = ((double) scrutin.getResult()) / ((double) scrutin.getNbVotants());
            if (resultat < 0.5) {
                resultat = 1 - resultat;
            }
        }
        progress.setProgress(resultat);
        resultat = Math.round(resultat*100);


        if (scrutin.getResult() > 0.5) {
            resultatLabel.setText("Résultat : " + scrutin.getOption2() + " (" + resultat + ")%");
        } else if (scrutin.getResult() < 0.5) {
            resultatLabel.setText("Résultat : " + scrutin.getOption1() + " (" + resultat + ")%");
        } else {
            resultatLabel.setText("Egalité");
        }
    }
}
