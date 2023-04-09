package com.clochelabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Proxy {

    public static ArrayList<User> users = new ArrayList<>();

    public static void updateProxy() {
        new Thread(() -> {
            System.out.println("Updating proxy ...");
            // Get every users from the database
            String query = "SELECT * FROM companyPoll";
            
            try {
                PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
                // Execute the query
                ResultSet rs = ps.executeQuery();
    
                // Clear the proxy
                users.clear();

                // Add every users to the proxy
                while (rs.next()) {
                    users.add(new User(rs.getString("mail"), rs.getString("password"), null));
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            System.out.println("Proxy updated");
            System.out.println("Users: " + users.size());
        }).start();
    }

    public static void initProxy() {
        System.out.println("Initializing proxy ...");
        updateProxy();
    }

    public static User getUserByMail(String mail) {
        for (User user : users) {
            if (user.getMail().equals(mail)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isUserExist(User user) {
        for (User u : users) {
            if (u.getMail().equals(user.getMail()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
