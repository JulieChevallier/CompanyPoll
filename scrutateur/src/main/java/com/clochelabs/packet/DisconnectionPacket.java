package com.clochelabs.packet;


public class DisconnectionPacket extends Packet {
    private String mail;
    private String password;
    private String token;

    public DisconnectionPacket(String mail, String password, String token) {
        super(PacketType.DISCONNECTION);
        this.mail = mail;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "DisconnectionPacket{" +
                "mail='" + mail + '\'' +
                '}';
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
