package com.clochelabs.view;

import com.clochelabs.Scrutin;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PollTitleAdmin extends PollTitle{

    @FXML
    private ImageView end;
    public PollTitleAdmin(Scrutin scrutin) {
        super(scrutin, "pollTitleAdmin.fxml");
        end.setOnMouseClicked(e->{
            PollManager.getInstance().close(scrutin);
            Client.setCenter(new PollResult(scrutin));
        });
    }
}
