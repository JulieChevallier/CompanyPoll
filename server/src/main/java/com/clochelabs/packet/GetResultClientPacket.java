package com.clochelabs.packet;

public class GetResultClientPacket extends Packet{
    private int idRef;
    public GetResultClientPacket(int idRef) {
        super(PacketType.GETRESULTCLIENT);
        this.idRef = idRef;
    }

    public int getIdRef() {
        return idRef;
    }

    @Override
    public String toString() {
        return null;
    }
}
