package com.clochelabs.packet;

public class SuccessPacket extends Packet {
    private String message;

    public SuccessPacket(String message) {
        super(PacketType.SUCCESS);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }


}
