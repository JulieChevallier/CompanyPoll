package com.clochelabs;

import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

import com.clochelabs.packet.Packet;

import javax.net.ssl.*;

public class Sender {
    private static Sender instance = null;

    public static Sender getInstance(){
        if(instance==null){
            synchronized (Sender.class){
                if(instance==null){
                    instance = new Sender();
                }
            }
        }
        return instance;
    }

    private Sender(){

    }

    public Packet send(Packet packet) {
        System.out.println("Sending : "+ packet.getType() + packet.toString());
        try {
//            Socket socket = new Socket(ConfServer.getAddress(), ConfServer.getPort());



            System.setProperty("javax.net.ssl.trustStore", "client/clienttruststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");

//            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            System.out.println(ConfServer.getAddress());
            SSLSocket socket =(SSLSocket) factory.createSocket(ConfServer.getAddress(), ConfServer.getPort());
            socket.startHandshake();


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
