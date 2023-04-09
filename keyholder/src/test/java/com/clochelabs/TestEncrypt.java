package com.clochelabs;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEncrypt {

    @Disabled
    private boolean test_publicKeyIsCorrect(PublicKey pk){
        BigInteger pPrime = pk.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        if(!pPrime.isProbablePrime(100)){
            return false;
        }
        if(!pk.getG().modPow(pPrime, pk.getP()).equals(BigInteger.ONE)){
            return false;
        }
        return true;
    }

    @Disabled
    private boolean test_privateKeyIsCorrect(PublicKey pk ,SecretKey sk){
        if(!pk.getG().modPow(sk.getX(), pk.getP()).equals(pk.getH())){
            return false;
        }
        return true;
    }



}
