package com.clochelabs.packet;

public class RemoveUserPacket extends Packet{
    private String mailOfRemovedUser;
    private String mailOfRemover;
    private String password;
    private String token;

    public RemoveUserPacket(String mailOfRemovedUser, String mailOfRemover, String password, String token) {
        super(PacketType.REMOVEUSER);
        this.mailOfRemovedUser = mailOfRemovedUser;
        this.mailOfRemover = mailOfRemover;
        this.password = password;
        this.token = token;
    }

    public String getMailOfRemovedUser() {
        return mailOfRemovedUser;
    }

    public String getMailOfAdder() {
        return mailOfRemover;
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
                "mailOfAddedUser='" + mailOfRemovedUser + '\'' +
                ", mailOfAdder='" + mailOfRemover + '\'' +
                '}';
    }
}