package com.clochelabs.packet;

import com.clochelabs.ScrutinDataObject;

import java.util.ArrayList;

public class GivePollPacket extends Packet{
    public ArrayList<ScrutinDataObject> scrutins;


    public GivePollPacket(ArrayList<ScrutinDataObject> scrutins) {
        super(PacketType.GIVEPOLL);
        this.scrutins = scrutins;
    }

    public ArrayList<ScrutinDataObject> getScrutins() {
        return scrutins;
    }




    @Override
    public String toString() {
        return" intitule.toString()";
    }
}
