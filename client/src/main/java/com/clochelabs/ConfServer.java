package com.clochelabs;

public class ConfServer {

    // Changez address par localhost si vous voulez mais laissez le commentaire au dessus merci.
    private static String address = "127.0.0.1"; // Server address (raspberry)

    //private static String address = "82.66.50.92";
    private static int port = 5056;

    public static String getAddress() {
        System.out.println(address);
        return address;
    }

    public static int getPort() {
        return port;
    }





























    // 82.66.50.92
}
