package com.clochelabs;

import com.clochelabs.packet.GetSharesPacket;
import com.clochelabs.packet.SendSharesPacket;
import com.clochelabs.packet.SetSharesPacket;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceManager {

    private static HashMap<Integer, Key[]> keysForRef = new HashMap<>();

    private static ArrayList<Integer> keyHoldersIPs= new ArrayList<>();

    private static PublicKey pk;

    private static int SERVERPORT = 5057;

    private static ServerSocket serverSocket;

    public static void run(){
        Socket socketForServer;
        keyHoldersIPs.add(5059);
        keyHoldersIPs.add(5061);
        keyHoldersIPs.add(5062);
        Key[] keys = Crypto.KeyGen(1000, 3, BigInteger.valueOf(10), 3);
        pk = (PublicKey) keys[0];
        setKeysForRef((KeyHolders) keys[1]);
        try {
            serverSocket = new ServerSocket(SERVERPORT);
            serverSocket.setSoTimeout(1);
        }catch (IOException i){
            i.printStackTrace();
        }

        while(true){
            try{
                socketForServer = serverSocket.accept();
            }catch (IOException e){
                socketForServer = null;
            }
            if(socketForServer!=null){
                runNewServerThread(socketForServer);
            }
        }
    }

    private static void setKeysForRef(KeyHolders holders){
        int i=0;
        for(Integer ip: keyHoldersIPs){
            Sender.send(new SetSharesPacket(holders.getHolders().get(i).getX(), holders.getHolders().get(i).getS() ), ip);
            i++;
        }
    }

    private static void runNewServerThread(Socket socketForServer) {
        Thread serverHandler = new ServerHandler(socketForServer);
        serverHandler.start();
    }

    public static void add(int id, Key[] keys){
        keysForRef.put(id, keys);
    }

    public static PublicKey getPk(){
        return pk;
    }

    public static ArrayList<KeyHolder> getHolders(){
        ArrayList<KeyHolder> keyHolders = new ArrayList<>();
        for(Integer ip : keyHoldersIPs){
            SendSharesPacket result =  (SendSharesPacket) Sender.send(new GetSharesPacket(), ip);
            keyHolders.add(new KeyHolder(result.getX(), result.getS()));
        }
        return keyHolders;
    }

    public static SecretKey getSk(int id){
        return ((SecretKey) (keysForRef.get(id)[1]));
    }

    public static boolean exists(int id){
        return keysForRef.containsKey(id);
    }
}
