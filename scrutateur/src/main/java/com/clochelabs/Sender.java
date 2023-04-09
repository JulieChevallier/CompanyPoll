package com.clochelabs;

import com.clochelabs.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {

    public static Packet send(Packet packet,String ip, int port) {
        try {
            Socket socket = new Socket(ip,port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(packet);
            Packet response = (Packet) in.readObject();
            System.out.println("Response received: " + response);
            out.close();
            in.close();
            socket.close();
            return response;
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return null;
    }
}