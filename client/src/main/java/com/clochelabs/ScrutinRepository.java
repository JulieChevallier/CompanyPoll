package com.clochelabs;



import com.clochelabs.packet.AddPollPacket;
import com.clochelabs.packet.GetPollPacket;
import com.clochelabs.packet.GivePollPacket;
import com.clochelabs.packet.Packet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ScrutinRepository {

    private HashMap<Integer, Scrutin> scrutins= new HashMap<>();

    private static ScrutinRepository instance = null;


    private ScrutinRepository(){
        getScrutins();
    }

    public static ScrutinRepository getInstance(){
        if(ScrutinRepository.instance == null){
            synchronized (ScrutinRepository.class){
                if(ScrutinRepository.instance == null){
                    ScrutinRepository.instance = new ScrutinRepository();
                }
            }
        }
        return ScrutinRepository.instance;
    }

    public ArrayList<ScrutinDataObject> getScrutinsDataObject(){
        GivePollPacket packet = ((GivePollPacket) Sender.getInstance().send(new GetPollPacket()));
        ArrayList<ScrutinDataObject> scrutins = packet.getScrutins();
        return scrutins;
    }

    public HashMap<Integer,Scrutin> getScrutins(){
        ArrayList<ScrutinDataObject> scrutins = getScrutinsDataObject();
        this.scrutins = new HashMap<>();
        for(ScrutinDataObject scrutinDataObject : scrutins){
            this.scrutins.put(scrutinDataObject.getId(), new Scrutin(scrutinDataObject));
        }
        return this.scrutins;
    }

    public ArrayList<Scrutin> getScrutinsAsList(){
        ArrayList<Scrutin> toReturn = new ArrayList<>();
        ArrayList<ScrutinDataObject> scrutins = getScrutinsDataObject();
        for(ScrutinDataObject scrutin : scrutins){
            toReturn.add(new Scrutin(scrutin));
        }
        return toReturn;
    }

    public Scrutin getScrutin(int id){
        return scrutins.get(id);
    }

    public void add(ScrutinDataObject poll){
        Packet packet = Sender.getInstance().send(new AddPollPacket(poll.getChoix1(), poll.getChoix2(), poll.getIntitule(), poll.getDateFin(), poll.getDateDebut()));
        if (packet.getType() == Packet.PacketType.SUCCESS) {
            getScrutins();
        }
    }

    public void add(String option1, String option2, String topic, String dateEnd, String dateBegin){
        add(new ScrutinDataObject(0, option1,option2,topic,dateEnd ,dateBegin));
    }

    public void close(Scrutin scrutin){
        scrutin.close();
        getScrutins();
    }

    public void close(int id){
        Scrutin scrutin = scrutins.get(id);
        scrutin.close();
        getScrutins();
    }


    public void setScrutins(HashMap<Integer, Scrutin> scrutins) {
        this.scrutins = scrutins;
    }
}
