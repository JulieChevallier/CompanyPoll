package com.clochelabs;

import com.clochelabs.packet.*;
import org.w3c.dom.css.CSSRule;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.*;

public class Urne {

    private static Urne instance = null;
    private static ArrayList<Scrutin> ref = new ArrayList<>();

    private PublicKey pk;


    private Urne(){
        requestPublicKey();
    }

    public final static Urne getInstance(){
        if(Urne.instance == null){
            synchronized (Urne.class){
                if(Urne.instance==null){
                    Urne.instance = new Urne();
                }
            }
        }
        return Urne.instance;
    }


    /**
     * Get the public key from the scrutineer
     */
    public void requestPublicKey(){
        Packet get = Sender.send(new GetKeyPacket(0), System.getenv("SCRUT_IP") != null ? System.getenv("SCRUT_IP") : "localhost", System.getenv("SCRUT_PORT") != null ? Integer.parseInt(System.getenv("SCRUT_PORT")) : 5057);
        switch (get.getType()){
            case ERROR -> {
                System.out.println("erreur : "+ ((ErrorPacket) get).getMessage());
            }
            case GIVEKEY -> {
                pk = ((GiveKeyPacket) get).getPk();
                System.out.println(pk);
            }
        }

    }

    public PublicKey getPublicKey(){
        return pk;
    }

    public ArrayList<PublicKey> getKeys() {
        ArrayList<PublicKey> key = new ArrayList<>();
        for(Scrutin s : ref){
            key.add(s.getKey());
        }
        return key;
    }
    public int newId() {

        int max = -1;
        for (Scrutin scrutinDataObject : ref) {
            if (max < scrutinDataObject.getIdScrutin()) {
                max = scrutinDataObject.getIdScrutin();
            }
        }
        return max+1;
    }

    public void newRef(String intitule,String choix1,String choix2,Date dateFin){
        System.out.println("New id : " + newId());
        Scrutin scrutin = new Scrutin(intitule,choix1,choix2,dateFin);
        ref.add(scrutin.getIdScrutin(), scrutin);
        System.out.println(ref);
    }

    public boolean addVote(BigInteger[] clientVote, Integer idVotant, int idScrutin){
        Scrutin scrutin = ref.get(idScrutin);
        if(scrutin == null){
            return false;
        }
        return scrutin.addVote(clientVote, idVotant);
    }

    public ArrayList<ScrutinDataObject> getRef() {
        ArrayList<ScrutinDataObject> scrutinDataObjects = new ArrayList<>();
        for(Scrutin re : ref){
            scrutinDataObjects.add(re.getDataObject());
        }
        return scrutinDataObjects;
    }

    public Scrutin getRef(int id) {
        return ref.get(id);
    }

    public int getResultPerRef(int id){
        return ref.get(id).decryptResult();
    }

    public int getNbVotantPerRef(int id){
        return ref.get(id).getNbVotants();
    }

    public void Update(){
        for(Scrutin s : ref){
            if(!s.isHasBeenClosed()){
                if(s.isEnded()){
                    closeScrutin(s.getIdScrutin());

                }
            }
        }
    }


    public void closeScrutin(int id){

        //envois du vote au scrutateur pour Decryptage et renvois
        ref.get(id).close();
    }


}

