package com.clochelabs;

import com.clochelabs.Scrutin;

import java.util.ArrayList;

public class Historique {
    private static ArrayList<Scrutin> hist = new ArrayList<>();

    public void rememberScrutin(Scrutin s){
        hist.add(s);
    }

    public ArrayList<Scrutin> getHist(){
        return hist;
    }
}
