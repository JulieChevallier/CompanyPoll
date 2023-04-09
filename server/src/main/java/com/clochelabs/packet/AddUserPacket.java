package com.clochelabs.packet;

public class AddUserPacket extends Packet{
    private String mailOfAddedUser;
    private String mailOfAdder;
    private String password;
    private String token;

    public AddUserPacket(String mailOfAddedUser, String mailOfAdder, String password, String token) {
        super(PacketType.ADDUSER);
        this.mailOfAddedUser = mailOfAddedUser;
        this.mailOfAdder = mailOfAdder;
        this.password = password;
        this.token = token;
    }

    public String getMailOfAddedUser() {
        return mailOfAddedUser;
    }

    public String getMailOfAdder() {
        return mailOfAdder;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "AddUserPacket{" +
                "mailOfAddedUser='" + mailOfAddedUser + '\'' +
                ", mailOfAdder='" + mailOfAdder + '\'' +
                '}';
    }
}
