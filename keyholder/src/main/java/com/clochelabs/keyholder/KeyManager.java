package com.clochelabs.keyholder;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class KeyManager {

    private static BigInteger x;
    private static BigInteger s;

    private static int SERVERPORT = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 5059;

    private static ServerSocket serverSocket;

    public static void run(){
        System.out.println("KeyManager running on port " + SERVERPORT);

        Socket socketForServer;
        try {
            serverSocket = new ServerSocket(SERVERPORT);
            serverSocket.setSoTimeout(1);
        }catch (IOException i){
            i.printStackTrace();
        }

        while (true){
            try {
                socketForServer = serverSocket.accept();
            }catch (IOException e){
                socketForServer = null;
            }
            if(socketForServer!=null){
                runNewScrutThread(socketForServer);
            }
        }
    }

    private static void runNewScrutThread(Socket socket){
        Thread serverHandler = new ScrutateurHandler(socket);
        serverHandler.start();
    }

    public static BigInteger getX() {
        return x;
    }

    public static BigInteger getS() {
        return s;
    }

    public static void setX(BigInteger x) {
        KeyManager.x = x;
    }

    public static void setS(BigInteger s) {
        KeyManager.s = s;
    }

    public static void main(String[] args) {
        KeyManager.run();
    }
}
