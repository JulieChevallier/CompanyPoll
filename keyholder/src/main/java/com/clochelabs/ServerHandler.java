package com.clochelabs;


import com.clochelabs.packet.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Socket socket;

    private int KEYLENGTH = 20;

    /*
    Builder for tests
     */
    public ServerHandler(){

    }

    public ServerHandler(Socket socket){
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
            switch(request.getType()){
                case GETRESULT -> {
                    System.out.println("result");
                    out.writeObject(getResult((GetResultPacket) request));
                }
                case GETKEY -> {
                    out.writeObject(getPublicKey((GetKeyPacket) request));
                }
                default -> throw new IllegalArgumentException("Unexpected value: " + request.getType());
            }
        }catch (IOException | ClassNotFoundException i){
            i.printStackTrace();
        }
    }

    public Packet getResult(GetResultPacket request) {
        if(ServiceManager.exists(request.getIdRef())){
            PublicKey pk = ServiceManager.getPk();
            int result = Crypto.Decrypt(request.getChiffre(), pk, ServiceManager.getHolders());
            return new GiveResultScrutPacket(result);
        }
        return new ErrorPacket("Ref id does not correspond to a ref");
    }

    public Packet getPublicKey(GetKeyPacket request){
        System.out.println(request.getIdRef());
        ServiceManager.add(request.getIdRef(), new Key[]{ServiceManager.getPk(), ServiceManager.getPk()});
        return new GiveKeyPacket(ServiceManager.getPk());
    }
}
