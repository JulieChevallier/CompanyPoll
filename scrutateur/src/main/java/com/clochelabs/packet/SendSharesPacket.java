package com.clochelabs.packet;

import java.math.BigInteger;

public class SendSharesPacket extends Packet{
    private BigInteger x;
    private BigInteger s;

    public SendSharesPacket(BigInteger x, BigInteger s){
        super(PacketType.SENDSHARES);
        this.x = x;
        this.s = s;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getS() {
        return s;
    }

    @Override
    public String toString() {
        return "GetSharesPacket{" +
                "x=" + x +
                ", s=" + s +
                '}';
    }
}
