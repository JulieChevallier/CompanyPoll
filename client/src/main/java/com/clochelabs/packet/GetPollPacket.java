package com.clochelabs.packet;

public class GetPollPacket extends Packet{


    public GetPollPacket() {
        super(PacketType.GETPOLL);
    }

    @Override
    public String toString() {
        return "GetPollPacket{}";
    }
}
