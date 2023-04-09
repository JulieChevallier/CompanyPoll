package com.clochelabs.packet;

import java.math.BigInteger;

public class SetSharesPacket extends Packet{
    private BigInteger x;
    private BigInteger s;

    public SetSharesPacket(BigInteger x, BigInteger s){
        super(PacketType.SETSHARES);
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
        return "SetSharesPacket{" +
                "x=" + x +
                ", s=" + s +
                '}';
    }
}
