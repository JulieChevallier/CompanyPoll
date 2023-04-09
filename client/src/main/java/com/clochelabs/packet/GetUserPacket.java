package com.clochelabs.packet;

public class GetUserPacket extends Packet{

    public GetUserPacket() {
        super(PacketType.GETUSER);
    }

    @Override
    public String toString() {
        return "GetUserPacket";
    }
}
