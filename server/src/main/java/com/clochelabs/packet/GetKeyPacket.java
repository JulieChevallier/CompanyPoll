package com.clochelabs.packet;

public class GetKeyPacket extends Packet {
    private int idRef;

    public GetKeyPacket(int idRef) {
        super(PacketType.GETKEY);
        this.idRef = idRef;
    }

    public int getIdRef() {
        return idRef;
    }

    @Override
    public String toString() {
        return "GetKeyPacket{" +
                "idRef=" + idRef +
                '}';
    }
}
