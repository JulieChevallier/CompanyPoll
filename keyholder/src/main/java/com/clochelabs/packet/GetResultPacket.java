package com.clochelabs.packet;

import java.math.BigInteger;

public class GetResultPacket extends Packet{
    private int idRef;
    private BigInteger[] chiffre;
    public GetResultPacket(int idRef, BigInteger[] chiffre) {
        super(PacketType.GETRESULT);
        this.chiffre = chiffre;
        this.idRef = idRef;
    }

    @Override
    public String toString() {
        return "idref = " + idRef;
    }

    public BigInteger[] getChiffre() {
        return chiffre;
    }

    public int getIdRef() {
        return idRef;
    }
}
