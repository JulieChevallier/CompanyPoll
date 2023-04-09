package com.clochelabs.packet;

public class GiveResultPacket extends Packet{
    private int result;
    private int nbVotants;

    public GiveResultPacket(int result, int nbVotants) {
        super(PacketType.GIVERESULT);
        this.result = result;
        this.nbVotants = nbVotants;
    }

    public int getResult() {
        return result;
    }

    public int getNbVotants() {
        return nbVotants;
    }

    @Override
    public String toString() {
        return "GiveResultPacket{" +
                "result=" + result +
                '}';
    }
}
