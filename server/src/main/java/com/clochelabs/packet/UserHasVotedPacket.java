package com.clochelabs.packet;

public class UserHasVotedPacket extends Packet{
    private String mail;

    private int idScrutin;

    public UserHasVotedPacket(String mail, int idScrutin) {
        super(PacketType.USER_HAS_VOTED);
        this.mail = mail;
        this.idScrutin = idScrutin;
    }

    public int getIdScrutin() {
        return idScrutin;
    }

    public String getMail() {
        return mail;
    }
    @Override
    public String toString() {
        return null;
    }
}
