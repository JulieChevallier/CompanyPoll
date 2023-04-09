package com.clochelabs;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class ServiceManager {
    private static int MAXTHREAD = 100;
    private static int nbOfThreads = 0;

    /** List of <b>currently</b> connected users
     * String is the mail of the user
     **/
    private static ArrayList<User> connectedUsers = new ArrayList<>();

    private static int CLIENTPORT = 5056;



    private static SSLServerSocket serverSocketClient;



    /**
     * Run the Server
     * Initialize serverSockets for Clients and the scrutateur
     * Then, start a new ClientThread
     */
    public static void run()  {


        //wesh flow détend toi
        System.setProperty("javax.net.ssl.keyStore", "serverkeystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");

//        System.setProperty("javax.net.ssl.trustStore", "clienttruststore.jks");
//        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        UpdateServer threadUpdate = new UpdateServer();
        threadUpdate.start();
        SSLSocket socketForClient;
        Socket socketForMobile;

        try {

            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();


            serverSocketClient = (SSLServerSocket) factory.createServerSocket(CLIENTPORT);
            serverSocketClient.setSoTimeout(1);
        }catch (IOException i){
            i.printStackTrace();
        }
        while(true){
            try{
                socketForClient = (SSLSocket) serverSocketClient.accept();
            }catch (IOException e){
                socketForClient = null;
            }
            if(socketForClient != null){
                runNewClientThread(socketForClient);
            }

        }
    }


    /**
     * Start a new client thread if there is less than MAXTHREAD other client threads running
     */
    public static void runNewClientThread(Socket clientSocket){

        while(nbOfThreads>=MAXTHREAD);
        nbOfThreads++;
        Thread clientThread = new ClientHandler(clientSocket);
        clientThread.start();//Start a client thread
    }

    /**
     * Start a new scrutateur thread if there is less than MAXTHREAD other scrutateur threads running
     */

    /**
     * Decrease the number of current thread running
     */
    public static void threadKilled(){
        nbOfThreads--;
    }


    public static void shutDown()
    {
        throw new RuntimeException("Shutting down");
    }


}


