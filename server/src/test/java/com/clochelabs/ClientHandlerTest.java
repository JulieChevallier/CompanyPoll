package com.clochelabs;


import com.clochelabs.packet.*;
import org.junit.Test;
import org.junit.Assert;


public class ClientHandlerTest{

    // Il faut refaire les tests pour les nouveaux packets / methods etc ...

    // @Test
    // public void testIfConnectWorkWithRightPasswordAdmin() throws UserNotConnectedException{
    //     //user to test is admin@test.com, dont change his password please -> mdp
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("mdp"), "admin@test.com", Crypto.generateToken());
    //     ClientHandler ch = new ClientHandler();
    //     Packet result=  ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, result.getType());
    //     SuccessPacket res = (SuccessPacket) result;
    //     Assert.assertEquals("isAdmin", res.getMessage());
    //     User user = ServiceManager.getUserByMail("admin@test.com");
    //     Assert.assertTrue(user.isConnected());
    //     ServiceManager.disconnectUser(user);
    // }
    // @Test
    // public void testIfConnectWorkWithRightPasswordNotAdmin() throws UserNotConnectedException{
    //     //user to test is admin@test.com, dont change his password please -> mdp
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("mdp"), "admin@test.com", Crypto.generateToken());
    //     ClientHandler ch = new ClientHandler();
    //     Packet result=  ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, result.getType());
    //     SuccessPacket res = (SuccessPacket) result;

    //     Assert.assertEquals("isAdmin", res.getMessage());
    //     User user = ServiceManager.getUserByMail("user@test.com"); // Utilisateur non connecté donc, user == null
    //     Assert.assertThrows(NullPointerException.class, () -> user.isConnected());
    // }
    // @Test
    // public void testIfConnectWorkWithWrongPasswordNotAdmin() throws UserNotConnectedException{
    //     //user to test is admin@test.com, dont change his password please -> mdp
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("fdp"), "user@test.com", Crypto.generateToken());
    //     ClientHandler ch = new ClientHandler();
    //     Packet result=  ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.ERROR, result.getType());
    // }

    // @Test
    // public void testIfConnectWorkWithWrongPasswordNotAdminAndInList() throws UserNotConnectedException{
    //     //user to test is admin@test.com, dont change his password please -> mdp
    //     ServiceManager.addUser(new User("user@test.com", "big", Crypto.generateToken()));
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("fdp"), "user@test.com", Crypto.generateToken());
    //     ClientHandler ch = new ClientHandler();
    //     Packet result=  ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.ERROR, result.getType());
    // }

    // @Test
    // public void testIfDisconnectWork() throws UserNotConnectedException{
    //     String token = Crypto.generateToken();
    //     //user to test is admin@test.com, dont change his password please -> mdp
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("mdp"), "user@test.com", token);
    //     ClientHandler ch = new ClientHandler();
    //     Packet result=  ch.connect(packet);
    //     DisconnectionPacket pack = new DisconnectionPacket("user@test.com", Crypto.sha256("mdp"), token);
    //     Packet res = ch.disconnect(pack);
    //     User user = ServiceManager.getUserByMail("user@test.com");
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, res.getType());
    //     Assert.assertFalse( user.isConnected());
    // }

    // @Disabled
    // @Test
    // public void testIfCanAddUser() throws UserNotConnectedException{
    //     String token = Crypto.generateToken();
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("mdp"), "admin@test.com", token);
    //     ClientHandler ch = new ClientHandler();
    //     Packet result = ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, result.getType());
    //     AddUserPacket add = new AddUserPacket("jamie@test.com", "admin@test.com", Crypto.sha256("mdp"), token);
    //     Packet resp = ch.addUser(add);
    //     User test = new User("jamie@test.com", Crypto.sha256("0000"), Crypto.generateToken());
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, resp.getType());
    //     Assert.assertTrue(test.connect());
    //     test.disconnect();
    //     DisconnectionPacket pack = new DisconnectionPacket("admin@test.com", Crypto.sha256("mdp"), token);
    //     Packet res = ch.disconnect(pack);
    // }

    // @Disabled
    // @Test
    // public void testIfCanRemoveUser() throws UserNotConnectedException{
    //     String token = Crypto.generateToken();
    //     AuthPacket packet = new AuthPacket(Crypto.sha256("mdp"), "admin@test.com", token);
    //     ClientHandler ch = new ClientHandler();
    //     Packet result = ch.connect(packet);
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, result.getType());
    //     RemoveUserPacket remove = new RemoveUserPacket("jamie@test.com", "admin@test.com", Crypto.sha256("mdp"), token);
    //     Packet resp = ch.removeUser(remove);
    //     User test = new User("jamie@test.com", Crypto.sha256("0000"), Crypto.generateToken());
    //     Assert.assertEquals(Packet.PacketType.SUCCESS, resp.getType());
    //     Assert.assertFalse(test.connect());
    //     DisconnectionPacket pack = new DisconnectionPacket("admin@test.com", Crypto.sha256("mdp"), token);
    //     Packet res = ch.disconnect(pack);
    // }
}
