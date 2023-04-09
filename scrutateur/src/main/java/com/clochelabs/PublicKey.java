package com.clochelabs;

import java.io.Serializable;
import java.math.BigInteger;

public class PublicKey implements Key, Serializable{
    private BigInteger p;
    private BigInteger g;
    private BigInteger h;

    public PublicKey(BigInteger p, BigInteger g, BigInteger h) {
        this.p = p;
        this.g = g;
        this.h = h;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getH() {
        return h;
    }

    @Override
    public String toString() {
        return "PublicKey{" +
                "p=" + p +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}
