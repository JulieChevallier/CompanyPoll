package com.clochelabs.packet;

public class AuthPacket extends Packet{
    private String password;
    private String mail;
    private String token;

    public AuthPacket(String password, String mail, String token) {
        super(PacketType.AUTH);
        this.password = password;
        this.mail=mail;
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthPacket{" +
                "password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getToken() {
        return token;
    }


}
