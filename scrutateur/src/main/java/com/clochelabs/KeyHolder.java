package com.clochelabs;

import com.clochelabs.Key;

import java.math.BigInteger;

public class KeyHolder implements Key {
    private BigInteger x;
    private BigInteger s;

    private int t;

    private int id;


    public KeyHolder(BigInteger x,  BigInteger s) {
        this.x = x;
        this.s = s;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getS() {
        return s;
    }

    public BigInteger computeD(BigInteger c, BigInteger p){
        return c.modPow(s,p);
    }


}