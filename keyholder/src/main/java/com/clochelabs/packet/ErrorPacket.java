package com.clochelabs.packet;

public class ErrorPacket extends Packet {
    private String message;

    public ErrorPacket( String message) {
        super(PacketType.ERROR);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorPacket{" +
                "message='" + message + '\'' +
                '}';
    }
}
