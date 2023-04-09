package com.clochelabs.packet;

public class SetLastNamePacket extends Packet {
    private String lastname;
    private String mail;
    private String mailAdmin;
    private String password;
    private String token;

    public SetLastNamePacket(String lastname, String mail, String mailAdmin, String password, String token) {
        super(PacketType.SETLASTNAME);
        this.lastname = lastname;
        this.mail = mail;
        this.mailAdmin = mailAdmin;
        this.password = password;
        this.token = token;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }

    public String getMailAdmin() {
        return mailAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "SetLastNamePacket{" +
                "firstname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", mailAdmin='" + mailAdmin + '\'' +
                '}';
    }
}
