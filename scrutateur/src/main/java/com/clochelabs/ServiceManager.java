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

    private static ArrayList<Integer> keyHoldersPORTs= new ArrayList<>();
    private static ArrayList<String> keyHoldersIPs= new ArrayList<>();

    private static PublicKey pk;

    private static int SERVERPORT = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 5057;

    private static ServerSocket serverSocket;

    public static void run(){
        Socket socketForServer;
        keyHoldersPORTs.add(System.getenv("PORT_KH1") != null ? Integer.parseInt(System.getenv("PORT_KH1")) : 5070);
        keyHoldersPORTs.add(System.getenv("PORT_KH2") != null ? Integer.parseInt(System.getenv("PORT_KH2")) : 5071);
        keyHoldersPORTs.add(System.getenv("PORT_KH3") != null ? Integer.parseInt(System.getenv("PORT_KH3")) : 5072);

        keyHoldersIPs.add(System.getenv("IP_KH1") != null ? System.getenv("IP_KH1") : "localhost");
        keyHoldersIPs.add(System.getenv("IP_KH2") != null ? System.getenv("IP_KH2") : "localhost");
        keyHoldersIPs.add(System.getenv("IP_KH3") != null ? System.getenv("IP_KH3") : "localhost");

        Key[] keys = Crypto.KeyGen(System.getenv("KEY_SIZE") != null ? Integer.parseInt(System.getenv("KEY_SIZE")) : 1000 , 3, BigInteger.valueOf(10), 3);
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
        for(int i=0; i<keyHoldersPORTs.size(); i++){
            Sender.send(new SetSharesPacket(holders.getHolders().get(i).getX(), holders.getHolders().get(i).getS() ), keyHoldersIPs.get(i), keyHoldersPORTs.get(i));
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
        for(int i=0; i<keyHoldersPORTs.size(); i++){
            SendSharesPacket result =  (SendSharesPacket) Sender.send(new GetSharesPacket(), keyHoldersIPs.get(i), keyHoldersPORTs.get(i));
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
