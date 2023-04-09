package com.clochelabs.packet;

import java.math.BigInteger;

public class VotePacket extends Packet{
    
    private String mail;
    private int idScrutin;
    private BigInteger[] vote;
    private String password;
    private String token;
    
    public VotePacket(String mail, int idScrutin, BigInteger[] vote, String password, String token){
        super(PacketType.VOTE);
        this.mail = mail;
        this.idScrutin = idScrutin;
        this.vote = vote;
        this.password = password;
        this.token = token;
    }
    
    public int getIdScrutin(){
        return idScrutin;
    }
    
    public BigInteger[] getVote() {
        return vote;
    }
    
    public void setIdScrutin(int idScrutin){
        this.idScrutin = idScrutin;
    }
    
    public void setVote(BigInteger[] vote) {
        this.vote = vote;
    }
    
    public PacketType getType(){
        return PacketType.VOTE;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "VotePacket{" +
                "idScrutin='" + idScrutin + '\'' +
                ", vote=" + vote +
                '}';
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}