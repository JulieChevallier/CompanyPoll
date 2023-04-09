package com.clochelabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> connectedUsers = new ArrayList<>();

    private static UserRepository instance = null;

    public static synchronized UserRepository getInstance(){
        if(instance==null){
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean addUser(User user){
        return connectedUsers.add(user);
    }

    public boolean isUserConnected(User user){
        return connectedUsers.contains(user);
    }

    public User getUser(User user){
        try {
            if(connectedUsers.isEmpty()){
                return null;
            }
            for(User u : connectedUsers){
                if(u.equals(user)){
                    return u;
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void disconnectUser(User user){
        user = getUser(user);
        connectedUsers.remove(user);
    }

    public User getUserByMail(String mail){
        for(User user : getInstance().connectedUsers){
            if(user.getMail().equals(mail)){
                return user;
            }
        }
        return null;
    }

    public ArrayList<String []> getUserList(){
        ArrayList<String[]> userList=new ArrayList<>();
        String query="SELECT mail,firstname,lastname FROM companyPoll";
        try{
            PreparedStatement ps=ServerDB.getCon().prepareStatement(query);
            try(ResultSet result = ps.executeQuery()){
                while(result.next()){
                    String[] infos={result.getString(1),result.getString(2), result.getString(3)};
                    userList.add(infos);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

}
