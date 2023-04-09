package com.clochelabs.packet;

import java.io.Serial;
import java.io.Serializable;

public abstract class Packet implements Serializable {
    @Serial
    private  static  final  long serialVersionUID =  1350092881346723535L;

    private final PacketType packetType;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    public PacketType getType(){
        return packetType;
    }

    public enum PacketType{
        AUTH, VOTE, DISCONNECTION, SETPASSWORD, SETNAME, ADDUSER, REMOVEUSER, ERROR, SUCCESS, GETRESULT, GETKEY, GIVERESULT, GIVEKEY, CONNECTIONSUCCESS, GETPOLL, GIVEPOLL, ADDPOLL, GETRESULTCLIENT, CLOSE, GETUSER, GIVEUSER, SETMAIL, SETLASTNAME
    }

    public abstract String toString();
}
