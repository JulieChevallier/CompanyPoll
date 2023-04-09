package com.clochelabs.packet;

import java.util.ArrayList;

public class GiverUserPacket extends Packet{
    private ArrayList<String[]> userList;

    public GiverUserPacket(ArrayList<String[]> userList) {
        super(PacketType.GIVEUSER);
        this.userList = userList;
    }

    public ArrayList<String[]> getUserList() {
        return userList;
    }


    @Override
    public String toString() {
        return "GiveUserPacket { "+userList+" }";
    }
}
