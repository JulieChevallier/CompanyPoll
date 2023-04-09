package com.clochelabs.packet;

public class GiveResultScrutPacket extends Packet{
    private int result;

    public GiveResultScrutPacket(int result) {
        super(PacketType.GIVERESULTSCRUT);
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "GiveResultPacket{" +
                "result=" + result +
                '}';
    }
}
