package com.clochelabs;

import java.util.ArrayList;

public class KeyHolders implements Key{
    private ArrayList<KeyHolder> holders;

    public KeyHolders(ArrayList<KeyHolder> holders){
        this.holders = holders;
    }

    public ArrayList<KeyHolder> getHolders() {
        return holders;
    }
}
