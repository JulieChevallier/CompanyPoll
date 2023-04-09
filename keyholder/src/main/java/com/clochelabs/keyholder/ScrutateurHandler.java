package com.clochelabs.keyholder;

import com.clochelabs.packet.Packet;
import com.clochelabs.packet.SendSharesPacket;
import com.clochelabs.packet.SetSharesPacket;
import com.clochelabs.packet.SuccessPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ScrutateurHandler extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Socket socket;

    public ScrutateurHandler(Socket socket){
        System.out.println("reçu");
        try {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            Packet request = (Packet) in.readObject();
            switch (request.getType()){
                case GETSHARES -> {
                    System.out.println("shares");
                    out.writeObject(sendShares());
                }
                case SETSHARES -> {
                    out.writeObject(setShares((SetSharesPacket) request));
                }
            }
        }catch (IOException | ClassNotFoundException i){
            i.printStackTrace();
        }
    }

    private Packet sendShares(){
        return new SendSharesPacket(KeyManager.getX(), KeyManager.getS());
    }

    private Packet setShares(SetSharesPacket packet){
        KeyManager.setX(packet.getX());
        KeyManager.setS(packet.getS());
        return new SuccessPacket("set shares");
    }
}
