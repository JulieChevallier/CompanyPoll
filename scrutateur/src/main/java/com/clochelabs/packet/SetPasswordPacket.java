package com.clochelabs.packet;


import com.clochelabs.Crypto;

public class SetPasswordPacket extends Packet {
    private String newPassword;
    private String mail;
    private String password;
    private String token;

    public SetPasswordPacket(String newPassword,String mail, String password, String token){
        super(PacketType.SETPASSWORD);
        this.newPassword = Crypto.sha256(newPassword);
        this.mail = mail;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "SetPasswordPacket{" +
                "newPassword='" + newPassword + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
