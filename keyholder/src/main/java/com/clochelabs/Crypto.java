package com.clochelabs;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Crypto {


    private static BigInteger p;
    private static BigInteger g;
    private static BigInteger x;
    private static int nbThread;
    private static int l;

    public static int getNbThread() {
        return nbThread;
    }

    public static void setNbThread(int nbThread) {
        Crypto.nbThread = nbThread;
    }

    public static void setP(BigInteger p) {
        Crypto.p = p;
    }

    public static int getL() {
        return l;
    }

    public static BigInteger L(List<BigInteger> all, int id, BigInteger x, BigInteger p) {
        BigInteger l = BigInteger.ONE;
        BigInteger xi = all.get(id);
        BigInteger num;
        BigInteger denom;
        BigInteger divide;
        for (int j = 0; j < all.size(); j++) {
            if (id != j) {
                num = x.subtract(all.get(j));
                denom = xi.subtract(all.get(j));
                divide = num.divide(denom);
                l = l.multiply(divide);
            }
        }
        return l.mod(p);
    }

    private static BigInteger polynome(BigInteger[] coefs, BigInteger x, BigInteger p) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < coefs.length; i++) {
            result = result.add((x.pow(i)).multiply(coefs[coefs.length - 1 - i]));
        }
        return result.mod(p);
    }

    private static BigInteger tirageP(int l) {
        Crypto.l = l;
        p = BigInteger.TWO;
        do {
            Thread n = new FindP();
            n.start();
        } while (!(p.subtract(BigInteger.ONE).divide(BigInteger.TWO)).isProbablePrime(100));
        System.out.println(p);
        return p;
    }

    private static BigInteger tirageG(BigInteger p) {
        boolean found = false;
        BigInteger g = new BigInteger(p.bitLength(), new Random());

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

    private static BigInteger tirageH(BigInteger p, BigInteger g, BigInteger x) {
        return g.modPow(x, p);
    }

    /**
     * KeyGen of Elgamal
     *
     * @param t    -> number of minimum available KeyHolder
     * @param n    -> number of shares
     * @param high -> the highest coefficient for polynome
     * @param bit  -> bit length of the key
     * @return publicKey and secretKey
     */
    public static Key[] KeyGen(int bit, int t, BigInteger high, int n) {

        BigInteger p = tirageP(bit);
        BigInteger g = tirageG(p);
        BigInteger x = tirageX(p);
        BigInteger h = tirageH(p, g, x);
        //choose p


        //polynome generation
        BigInteger[] coefs = new BigInteger[t];
        for (int i = 0; i < t - 1; i++) {
            coefs[i] = BigInteger.valueOf(3);

        }
        coefs[t - 1] = x;
        System.out.println(Arrays.toString(coefs));

        BigInteger[] shares = new BigInteger[n];
        ArrayList<KeyHolder> holders = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            holders.add(new KeyHolder(BigInteger.valueOf(i), polynome(coefs, BigInteger.valueOf(i), p)));
        }



        return new Key[]{new PublicKey(p, g, h), new KeyHolders(holders)};
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

    private static BigInteger[] produireC(BigInteger g, BigInteger r, BigInteger p, BigInteger h, int m) {

        BigInteger c[] = {BigInteger.ZERO, BigInteger.ZERO};
        c[0] = g.modPow(r, p);
        BigInteger pt1 = g.modPow(BigInteger.valueOf(m), p);
        BigInteger pt2 = h.modPow(r, p);
        c[1] = pt1.multiply(pt2);

        return c;
    }

    private static BigInteger tirageR(BigInteger p) {
        return tirageX(p);
    }

    public static BigInteger[] Encrypt(PublicKey pk, int m) {
        BigInteger r = tirageR(pk.getP());
        return produireC(pk.getG(), r, pk.getP(), pk.getH(), m);
    }

    public static int Decrypt(BigInteger[] c, PublicKey key, ArrayList<KeyHolder> holders) {
        List<BigInteger> x = new ArrayList<>();
        for(int i=0; i<holders.size(); i++){
            x.add(holders.get(i).getX());
        }
        List<BigInteger> alphas = new ArrayList<>();
        BigInteger l = BigInteger.ONE;
        for(int i=0; i< holders.size(); i++){
            alphas.add(L(x, i, BigInteger.valueOf(0), key.getP())); //calcul Ai
        }

        List<BigInteger> d = new ArrayList<>();
        for(int i=0; i<holders.size(); i++){
            d.add(c[0].modPow(holders.get(i).getS(), key.getP()));
        }
        BigInteger drill = BigInteger.ONE;
        for(int i=0; i<holders.size(); i++){
            drill = drill.multiply(d.get(i).modPow(alphas.get(i), key.getP()));
        }

        BigInteger m = c[1].multiply(drill.modInverse(key.getP())).mod(key.getP());

        return produirem(m,key.getP(), key.getG());
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

    public static int DecryptBasic(PublicKey pk, SecretKey sk, BigInteger[] message) {
        BigInteger M = produireM(message[0], message[1], sk.getX(), pk.getP());
        return produirem(M, pk.getP(), pk.getG());
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



}