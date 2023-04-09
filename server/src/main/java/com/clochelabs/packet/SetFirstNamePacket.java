package com.clochelabs.packet;

public class SetFirstNamePacket extends Packet{
    private String firstname;
    private String mail;
    private String mailAdmin;
    private String password;
    private String token;

    public SetFirstNamePacket(String firstname, String mail, String mailAdmin, String password, String token) {
        super(PacketType.SETNAME);
        this.firstname = firstname;
        this.mail = mail;
        this.mailAdmin = mailAdmin;
        this.password = password;
        this.token = token;
    }

    public String getFirstname() {
        return firstname;
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
        return "SetFirstNamePacket{" +
                "firstname='" + firstname + '\'' +
                ", mail='" + mail + '\'' +
                ", mailAdmin='" + mailAdmin + '\'' +
                '}';
    }
}
