package com.clochelabs;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {
    
    public UserTest(String testName){super(testName);}


    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    @Test
    public void testIfUserCanConnectWithRightAuth(){
        User test = new User("flow@gmail.com",Crypto.sha256("French Riven"), Crypto.generateToken());
        assertTrue(test.connect());
        assertEquals(test.getLastName(), "Flow");
        assertEquals(test.getFirstName(), "Montourcyf");
    }

    @Test
    public void testIfUserCantConnectWithWrongAuthMail(){
        User test = new User("radul@tet.com","50$cent", Crypto.generateToken());
        assertFalse(test.connect());
        assertNull(test.getLastName());
        assertNull(test.getFirstName());
    }

    @Test
    public void testIfUserCantConnectWithWrongAuthPassword(){
        User test = new User("radul@test.com","50cent", Crypto.generateToken());
        assertFalse(test.connect());
        assertNull(test.getLastName());
        assertNull(test.getFirstName());
    }

    @Test
    public void testIfUserIsAnAdminWhenRight(){
        User test = new User("radul@test.com", "50$cent",Crypto.generateToken());
        assertTrue(test.checkIfUserIsAnAdmin());
    }

    @Test
    public void testCheckIfUserIsValid(){
        User test = new User("flow@gmail.com", Crypto.sha256("French Riven"),Crypto.generateToken());
        assertTrue(test.checkIfUserIsValid());
    }

    @Test
    public void testIfMailAlreadyExistsInDB(){
        assertTrue(User.checkIfMailAlreadyExistsInDB("flow@gmail.com"));
    }
}
