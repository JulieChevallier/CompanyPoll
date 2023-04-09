package com.clochelabs;

import java.io.Serial;
import java.io.Serializable;


public class ScrutinDataObject implements Serializable {
    @Serial
    private  static  final  long serialVersionUID =  1350092881346723535L;

    private int id;

    private final String choix1;

    private final String choix2;

    private final String intitule;

    private String dateFin;

    private String dateDebut;


    public ScrutinDataObject(int id, String choix1, String choix2, String intitule, String dateFin, String dateDebut) {
        this.id = id;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.intitule = intitule;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;

    }

    public int getId() {
        return id;
    }

    public String getChoix1() {
        return choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }
}
