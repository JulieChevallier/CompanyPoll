package com.clochelabs;

public class Server {
    public static void main(String[] args) {
        System.out.println("Starting server ...");
        // print ASCII art of "CompanyPoll"
        System.out.println(" _____                                          ______     _ _ \n" +
                "/  __ \\                                         | ___ \\   | | |\n" +
                "| /  \\/ ___  _ __ ___  _ __   __ _ _ __  _   _  | |_/ /__ | | |\n" +
                "| |    / _ \\| '_ ` _ \\| '_ \\ / _` | '_ \\| | | | |  __/ _ \\| | |\n" +
                "| \\__/\\ (_) | | | | | | |_) | (_| | | | | |_| | | | | (_) | | |\n" +
                " \\____/\\___/|_| |_| |_| .__/ \\__,_|_| |_|\\__, | \\_|  \\___/|_|_|\n" +
                "                      | |                 __/ |                \n" +
                "                      |_|                |___/                 ");

        // Update the Proxy
        Proxy.initProxy();

        ServiceManager.run();

    }
}