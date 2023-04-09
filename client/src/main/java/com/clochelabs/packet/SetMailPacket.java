package com.clochelabs.packet;

public class SetMailPacket extends Packet{
    private String oldMail;
    private String newMail;
    private String password;
    private String token;

    public SetMailPacket(String oldMail, String newMail, String password, String token) {
        super(PacketType.SETMAIL);
        this.oldMail = oldMail;
        this.newMail = newMail;
        this.password = password;
        this.token = token;
    }

    public String getOldMail() {
        return oldMail;
    }

    public String getNewMail() {
        return newMail;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "SetMailPacket{" +
                "oldMail='" + oldMail + '\'' +
                ", newMail='" + newMail + '\'' +
                '}';
    }
}
