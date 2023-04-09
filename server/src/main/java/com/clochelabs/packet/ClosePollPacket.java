package com.clochelabs.packet;

public class ClosePollPacket extends Packet{
    private int idPoll;

    public ClosePollPacket(int idPoll) {
        super(PacketType.CLOSE);
        this.idPoll = idPoll;
    }

    public int getIdPoll() {
        return idPoll;
    }

    @Override
    public String toString() {
        return "ClosePollPacket{" +
                "idPoll=" + idPoll +
                '}';
    }
}
