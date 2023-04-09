package com.clochelabs.view;

import com.clochelabs.*;

import java.util.ArrayList;

public class PollManager {

    private ViewSide side;
    private static PollManager instance=null;

    public void setSide(ViewSide side) {
        this.side = side;
        updatePolls();

    }

    private PollManager() {

    }

    public static PollManager getInstance(){
        if(PollManager.instance == null){
            synchronized (PollManager.class){
                if(PollManager.instance == null){
                    PollManager.instance = new PollManager();
                }
            }
        }
        return PollManager.instance;
    }

    public ArrayList<ScrutinDataObject> getScrutin(){
        return ScrutinRepository.getInstance().getScrutinsDataObject();
    }


    public void add(String option1, String option2, String topic, String dateEnd, String dateBegin) {
        ScrutinRepository.getInstance().add(option1, option2, topic, dateEnd, dateBegin);
        //implement action to add to viewable list
        updatePolls();
    }

    private void updatePolls() {
        while (side.getPolls().getChildren().size() > 0) {
            side.getPolls().getChildren().remove(0);
        }
        ArrayList<Scrutin> scrutins1 = ScrutinRepository.getInstance().getScrutinsAsList();
        side.updatePollList();
    }

    public void close(Scrutin scrutin) {
        ScrutinRepository.getInstance().close(scrutin);

        updatePolls();

    }
}
