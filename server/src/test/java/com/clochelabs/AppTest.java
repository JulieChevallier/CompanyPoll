package com.clochelabs;

import java.math.BigInteger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    /*
    public void testKeyGen() {
        Key[] key = Crypto.KeyGen(102);
        assertTrue(key.length == 4);
    }

    public void testEncrypt() {
        Key[] key = Crypto.KeyGen(102);
        BigInteger[] C = Crypto.Encrypt(BigInteger.valueOf(1), key);
        assertTrue(C.length == 2);
    }

    public void testDecrypt() {
        Key[] key = Crypto.KeyGen(102);
        BigInteger[] C = Crypto.Encrypt((PublicKey) key[0],1);
        BigInteger M = Crypto.Decrypt(C, key);
        assertTrue(!M.equals(BigInteger.valueOf(0)));
    }

    public void testAgrege() {
        Key[] key = Crypto.KeyGen(102);
        BigInteger[] C1 = Crypto.Encrypt((PublicKey) key[0],1);
        BigInteger[] C2 = Crypto.Encrypt((PublicKey) key[0],1);
        BigInteger[] C = Crypto.Agrege(C1, C2, key);
        BigInteger M = Crypto.Decrypt(C, key);
        assertTrue(!M.equals(BigInteger.valueOf(0)));
    }
    */
}
