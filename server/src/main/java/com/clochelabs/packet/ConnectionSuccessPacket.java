package com.clochelabs.packet;

public class ConnectionSuccessPacket extends Packet{
    private String firstName;
    private String lastName;

    private boolean isAdmin;

    public String getFirstName() {
        return firstName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return "ConnectionSuccessPacket{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public ConnectionSuccessPacket(String firstName, String lastName, boolean isAdmin){
        super(PacketType.CONNECTIONSUCCESS);
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;

    }
}
