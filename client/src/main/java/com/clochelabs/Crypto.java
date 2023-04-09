package com.clochelabs;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Crypto {

    private static BigInteger tirageP(int l) {
        BigInteger p, pPrime;
        do {
            p = BigInteger.probablePrime(l, new Random());
            pPrime = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
        } while (!pPrime.isProbablePrime(100));
        return p;
    }

    private static BigInteger tirageG(BigInteger p) {
        boolean found = false;
        BigInteger g = new BigInteger(p.bitLength(), new Random());
        ;
        BigInteger pPrime = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
        while (!found) {
            BigInteger remainder = g.modPow(pPrime, p);
            if (!(g.compareTo(p) < 0 && g.compareTo(BigInteger.ONE) > 0)) {
                g = new BigInteger(p.bitLength(), new Random());
            } else {
                if (remainder.equals(BigInteger.ONE)) {
                    found = true;
                } else {
                    g = new BigInteger(p.bitLength(), new Random());
                }
            }
        }
        return g;
    }

    private static BigInteger tirageX(BigInteger p) {
        boolean found = false;
        BigInteger pPrime = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
        BigInteger x = new BigInteger(pPrime.bitLength(), new Random());
        while (!found) {
            if (x.compareTo(pPrime) < 0 && x.compareTo(BigInteger.ZERO) >= 0) {
                found = true;
            } else {
                x = new BigInteger(pPrime.bitLength(), new Random());
            }
        }

        return x;
    }

    private static BigInteger tirageH(BigInteger p, BigInteger g, BigInteger x) {
        return g.modPow(x, p);
    }

    private static BigInteger tirageR(BigInteger p) {
        return tirageX(p);
    }

    private static BigInteger[] produireC(BigInteger g, BigInteger r, BigInteger p, BigInteger h, int m) {

        BigInteger c[] = {BigInteger.ZERO, BigInteger.ZERO};
        c[0] = g.modPow(r, p);
        BigInteger pt1 = g.modPow(BigInteger.valueOf(m), p);
        BigInteger pt2 = h.modPow(r, p);
        c[1] = pt1.multiply(pt2);

        return c;
    }

    private static BigInteger produireM(BigInteger u, BigInteger v, BigInteger x, BigInteger p) {
        BigInteger v1p = v.mod(p);
        BigInteger uxinv = (u.pow(x.intValue())).modInverse(p);
        return v1p.multiply(uxinv).mod(p);
    }

    private static int produirem(BigInteger M, BigInteger p, BigInteger g) {
        BigInteger m = BigInteger.ZERO;
        BigInteger mReel = new BigInteger(String.valueOf(8));
        while (!M.equals(g.modPow(m, p))) {
            m = m.add(BigInteger.ONE);
        }
        return m.intValue();
    }


    public static BigInteger[] Agrege(BigInteger[] c1, BigInteger[] c2, BigInteger p) {
        BigInteger c[] = {BigInteger.ZERO, BigInteger.ZERO};
        BigInteger uP = c1[0].mod(p);
        BigInteger uPrimeP = c2[0].mod(p);
        c[0] = uP.multiply(uPrimeP);

        BigInteger vP = c1[1].mod(p);
        BigInteger vPrimeP = c2[1].mod(p);
        c[1] = vP.multiply(vPrimeP);

        return c;
    }

    public static Key[] KeyGen(int l) {
        BigInteger p = Crypto.tirageP(l);
        BigInteger g = Crypto.tirageG(p);
        BigInteger x = Crypto.tirageX(p);
        BigInteger h = Crypto.tirageH(p, g, x);

        return new Key[]{new PublicKey(p, g, h), new SecretKey(x)};
    }

    public static BigInteger[] Encrypt(PublicKey pk, int m) {
        BigInteger r = Crypto.tirageR(pk.getP());
        return Crypto.produireC(pk.getG(), r, pk.getP(), pk.getH(), m);
    }

    public static int Decrypt(PublicKey pk, SecretKey sk, BigInteger[] message) {
        BigInteger M = Crypto.produireM(message[0], message[1], sk.getX(), pk.getP());
        return Crypto.produirem(M, pk.getP(), pk.getG());
    }

    public static String sha256(String msg){
        try {


            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(msg.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 64) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate a string containing a random sequence of characters including letters and numbers in lower or upper case.
     * @return
     */
    public static String generateToken(){
        String token = "";

        for(int i = 0; i < 32; i++){
            int random = (int) (Math.random() * 62);
            if(random < 10){
                token += random;
            }else if(random < 36){
                token += (char) (random + 55);
            }else{
                token += (char) (random + 61);
            }
        }

        return token;
    }

}