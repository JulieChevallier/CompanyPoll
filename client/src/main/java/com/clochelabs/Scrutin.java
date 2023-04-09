package com.clochelabs;

import com.clochelabs.packet.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Scrutin {
    private final BooleanProperty validityProperty = new SimpleBooleanProperty();
    private int idScrutin;
    private String option1;
    private String option2;
    private String topic;
    private Date dateBegin;
    private Date dateEnd;

    private int result;

    private int nbVotants;

    public Scrutin(int idScrutin, String option1, String option2, String topic, Date dateBegin, Date dateEnd) {
        this.idScrutin = idScrutin;
        this.option1 = option1;
        this.option2 = option2;
        this.topic = topic;
        this.dateBegin = Date.from(Instant.now());
        System.out.println(dateEnd);
        this.dateEnd = dateEnd;
        if(isDone()){
            getResult();
        }
    }

    public Scrutin(ScrutinDataObject scrutinDataObject){
        this(scrutinDataObject.getId(), scrutinDataObject.getChoix1(), scrutinDataObject.getChoix2(), scrutinDataObject.getIntitule(), DateUtils.stringToDate(scrutinDataObject.getDateDebut()),DateUtils.stringToDate(scrutinDataObject.getDateFin()));

    }

    public Scrutin(int idScrutin, boolean option){
        this.idScrutin = idScrutin;
        if(option) this.option1 = "true";
        else this.option2 = "false";
    }

    public boolean isDone(){
        return dateEnd.before(new Date());
    }

    public String getTopic() {
        return topic;
    }

    public BooleanProperty getValidityProperty(){
        return validityProperty;
    }


    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public ScrutinDataObject getDataObject(){
        return new ScrutinDataObject(idScrutin,option1, option2, topic, DateUtils.dateToString(dateEnd), DateUtils.dateToString(dateBegin));
    }

    public void askForResult(){
        GiveResultPacket response = (GiveResultPacket) Sender.getInstance().send(new GetResultClientPacket(idScrutin));
        System.out.println("resultat = "  + response.getResult() + " votants = " + response.getNbVotants() );
        this.result = response.getResult();
        this.nbVotants = response.getNbVotants();
    }

    public int getResult() {
        return result;
    }

    public int getNbVotants() {
        return nbVotants;
    }

    public void close(){
        Sender.getInstance().send(new ClosePollPacket(idScrutin));
    }

    public int getId() {
        return idScrutin;
    }

    public PublicKey getPublicKey() {
        GetKeyPacket request = new GetKeyPacket(getId());
        Packet packet = Sender.getInstance().send(request);
        if(packet instanceof GiveKeyPacket publicKeyPacket){
            return publicKeyPacket.getPk();
        }
        else if (packet instanceof ErrorPacket){
            ErrorPacket errorPacket = (ErrorPacket) packet;
            System.out.println(errorPacket);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Scrutin{" +
                "validityProperty=" + validityProperty +
                ", idScrutin=" + idScrutin +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", topic='" + topic + '\'' +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                ", result=" + result +
                ", nbVotants=" + nbVotants +
                '}';
    }
}
