package com.clochelabs;

import com.clochelabs.packet.*;
import com.clochelabs.packet.Packet.PacketType;

import java.math.BigInteger;
import java.util.ArrayList;

public class User {

    private static volatile User instance = null;
    private String firstName;
    private String lastName;
    private String password;
    private String mail;
    private String tokenSession;

    private boolean isAdmin;
    protected boolean isConnected;

    private User() {
        isConnected=false;
        tokenSession = Crypto.generateToken();
    }

    public static User getInstance(){
        if(User.instance == null){
            synchronized (User.class){
                if(User.instance == null){
                    User.instance = new User();
                }
            }
        }
        return User.instance;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * 
     * @param idScrutin
     * @param vote (boolean) 0 = false, 1 = true
     * @return (boolean) true if vote is valid, false if not
     */
    public boolean vote(int idScrutin, int vote){

        // Getting the public key from the server
        System.out.println("Getting the public key from the server ...");
        Scrutin scrutin = ScrutinRepository.getInstance().getScrutin(idScrutin);
        PublicKey publicKey = scrutin.getPublicKey();
        if(publicKey == null){
            return false;
        }

        BigInteger[] cryptedMessage = Crypto.Encrypt(publicKey, vote);

        // The verification of the vote is done by the server so no need to check anything here
        VotePacket votePacket = new VotePacket(mail, idScrutin, cryptedMessage, password, tokenSession);

        System.out.println("Sending vote to server...");
        Packet response = Sender.getInstance().send(votePacket);

        return response.getType() == PacketType.SUCCESS;
    }



    /**
     * <p>Connect the user to the server using his credentials</p>
     * @return <code>true</code> if the connection is successful, <code>false</code> otherwise
     */
    public boolean connect(){
        System.out.println("Trying to connect ...");
        AuthPacket request = new AuthPacket(password, mail, tokenSession);
        Packet response = Sender.getInstance().send(request);
        if(response.getType()==PacketType.CONNECTIONSUCCESS){
            firstName = ((ConnectionSuccessPacket) response).getFirstName();
            lastName = ((ConnectionSuccessPacket) response).getLastName();
            isAdmin = ((ConnectionSuccessPacket) response).isAdmin();
            isConnected = true;
            return true;
        }else if(response.getType()==PacketType.ERROR){
            System.out.println("Error: " + response);
            return false;
        }

        return false;
    }

    public boolean disconnect(){
        DisconnectionPacket request = new DisconnectionPacket(mail, password, tokenSession);
        Packet packet = Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            isConnected = false;
            return true;
        }
        return false;
    }

    /**
     * <p>Set the user's first name in the database. This name will be found thanks to the user's mail</p>
     * <p>Only an admin can change a user's name. An admin can change any name</p>
     * @param firstName
     */
    public boolean setFirstName(String firstName, String mail) {
        SetFirstNamePacket request=new SetFirstNamePacket(firstName,mail,this.mail, password, tokenSession);
        Packet packet=Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            this.firstName=firstName;
            return true;
        }
        return false;
    }

    public boolean addUser(String mailToAdd) {
        AddUserPacket request=new AddUserPacket(mailToAdd,mail, password, tokenSession);
        Packet packet=Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            return true;
        }
        return false;
    }

    public boolean removeUser(String mailToRemove) {
        RemoveUserPacket request=new RemoveUserPacket(mailToRemove,mail, password, tokenSession);
        Packet packet=Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            return true;
        }
        return false;
    }

    /**
     * <p>Set the user's last name</p>
     * @param lastName
     */
    public boolean setLastName(String lastName,String mailToChange) {
        SetLastNamePacket request=new SetLastNamePacket(lastName,mailToChange,mail, password, tokenSession);
        System.out.println(request);
        Packet packet=Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            this.lastName=lastName;
            return true;
        }
        return false;
    }

    public boolean setMail(String mail) {
        SetMailPacket request=new SetMailPacket(this.mail,mail, password, tokenSession);
        Packet packet=Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            this.mail=mail;
            return true;
        }
        return false;
    }

    public void setMailForLogin(String mail) {
        this.mail=mail;
    }

    // TODO: Peut causer des problèmes de sécurité
    public boolean setPassword(String oldPassword, String newPassword){
        oldPassword = Crypto.sha256(oldPassword);
        newPassword = Crypto.sha256(newPassword);
        SetPasswordPacket request = new SetPasswordPacket(newPassword, mail, oldPassword, tokenSession);
        Packet packet = Sender.getInstance().send(request);
        if(packet.getType()==PacketType.SUCCESS){
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public void setPassword(String password) {
        this.password = Crypto.sha256(password);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String[]> getUserList(){
        Packet packet=Sender.getInstance().send(new GetUserPacket());
        if(packet instanceof GiverUserPacket){
            return ((GiverUserPacket) packet).getUserList();
        }
        else if (packet instanceof ErrorPacket){
            System.out.println((ErrorPacket) packet);
        }
        return null;
    }

}
