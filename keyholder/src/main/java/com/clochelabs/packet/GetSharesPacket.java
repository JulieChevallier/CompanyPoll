package com.clochelabs.packet;

public class GetSharesPacket extends Packet{
    public GetSharesPacket(){
        super(PacketType.GETSHARES);
    }


    public String toString(){
        return "GETSHARES";
    }
}
