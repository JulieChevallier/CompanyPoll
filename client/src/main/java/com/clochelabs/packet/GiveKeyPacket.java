package com.clochelabs.packet;


import com.clochelabs.PublicKey;

public class GiveKeyPacket extends Packet
{
    private PublicKey pk;

    public GiveKeyPacket(PublicKey pk) {
        super(PacketType.GIVEKEY);
        this.pk = pk;
    }

    public PublicKey getPk() {
        return pk;
    }

    @Override
    public String toString() {
        return "GiveKeyPacket{" +
                "pk=" + pk +
                '}';
    }
}
