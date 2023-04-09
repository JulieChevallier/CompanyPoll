package com.clochelabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class User {
    private String lastName;
    private String firstName;
    private String mail;
    private String password;
    private String tokenSession;
    private String defaultPassword = "0000";

    private boolean connectionState;
    private boolean isAdmin;

    public User(String mail, String password, String tokenSession) {
        this.mail = mail;
        this.password = password;
        this.tokenSession = tokenSession;
    }

    /**
     * This method is static because it is used to check if a mail is already in the database so we don't have to create a new user
     * @param mail of user
     * @return true if the mail correspond to a user in the database. Else return false
     */
    public static boolean checkIfMailAlreadyExistsInDB(String mail){

        // Check the Proxy first
        if(Proxy.getUserByMail(mail) != null){
            return true;
        }

        System.out.println("Mail not found in Proxy");
        System.out.println("Checking in DB ...");

        // We need to check the database if the mail is not in the Proxy

        String query= "SELECT COUNT(*) AS nbMail FROM companyPoll WHERE mail= ?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("nbMail")==1; // if the mail is in the database, return true
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Mail not found in DB");
        return false;
    }

    /**
     * Send a request to the DB to check user mail and password are correct
     * @return true if the user is valid
     */
    public boolean checkIfUserIsValid(){

        // Check the Proxy first
        if(Proxy.isUserExist(this)){
            System.out.println("User found in Proxy");
            return true;
        }

        System.out.println("User not found in Proxy");
        System.out.println("Checking in DB ...");

        // We need to check the database if the user is not in the Proxy

        String queryExists = "SELECT COUNT(*) AS nbUser FROM companyPoll WHERE mail=?";
        String queryPassword = "SELECT password FROM companyPoll WHERE mail=?";
        try{
            PreparedStatement ps = ServerDB.getCon().prepareStatement(queryExists);
            ps.setString(1, mail);


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getInt("nbUser")==1){
                    PreparedStatement psPassword = ServerDB.getCon().prepareStatement(queryPassword);
                    psPassword.setString(1,mail);

                    ResultSet rsPassword = psPassword.executeQuery();

                    while(rsPassword.next()){
                        if(rsPassword.getString("password").equals(password)){
                            Proxy.updateProxy();
                            return true;
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
                
        return false;
    }



    public boolean checkIfUserIsAnAdmin(){
        String query = "SELECT COUNT(*) AS nbAdmin FROM companyPoll WHERE mail=? AND isAdmin=1";

        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1,mail);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                isAdmin =  rs.getInt("nbAdmin") == 1;
                return isAdmin;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        isAdmin = false;
        return isAdmin;
    }

    /**
     * A partir des attributs du User, connect va voir si les informations de connexion sont valides
     * @return true si la connexion s'est effectuée, false sinon
     */
    public boolean connect(){
        if(checkIfUserIsValid()){
            getInfoFromDb();
            connectionState = true;
            UserRepository.getInstance().addUser(this);
            return true;
        }
        return false;
    }

    public boolean isConnected() {
        return connectionState;
    }

    /**
     * get lastName and firstName from the database
     */
    public void getInfoFromDb(){
        String query = "SELECT firstName, lastName FROM companyPoll WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                this.lastName = rs.getString("lastName");
                this.firstName = rs.getString("firstName");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Get the ID of the user
     */
    public int getId(){
        String query = "SELECT id FROM companyPoll WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Update the DB to set a new password for the user
     * @param newPassword
     * @throws UserNotConnectedException
     */
    public void setPassword(String newPassword) throws UserNotConnectedException{
        password=newPassword;
        String query = "UPDATE companyPoll SET password=? WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, password);
            ps.setString(2, mail);
            ps.executeUpdate();

            Proxy.updateProxy();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    
    /**
     * Update the DB to set a new first name for the user
     * @param mailToManage mail of the user to manage
     * @param newFirstName new first name
     */
    public boolean setFirstName(String mailToManage, String newFirstName) {

        firstName=newFirstName;
        String query = "UPDATE companyPoll SET firstName=? WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, mailToManage);
            ps.executeUpdate();
            
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            
            return false;
        }
        
    }

    /**
     * Update the DB to set a new last name for the user
     * @param mailToManage mail of the user to manage
     * @param newLastName new last name
     */
    public boolean setLastName(String mailToManage, String newLastName) {
        
        lastName=newLastName;
        String query = "UPDATE companyPoll SET lastName=? WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, lastName);
            ps.setString(2, mailToManage);
            ps.executeUpdate();
            
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            
            return false;
        }
    }

    public boolean setMail(String mailToManage, String newMail) {

        mail=newMail;
        String query = "UPDATE companyPoll SET mail=? WHERE mail=?";
        try {
            PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
            ps.setString(1, mail);
            ps.setString(2, mailToManage);
            ps.executeUpdate();

            Proxy.updateProxy();

            return true;
        }catch (SQLException e){
            e.printStackTrace();

            return false;
        }
    }
    
    /**
     * insert a new user into the table
     * @param mail of a user
     * @return true if the operation worked, false if there is already a user in the db corresponding to the given mail
     */
    public boolean addUser(String mail){

        if(!checkIfMailAlreadyExistsInDB(mail)){
            System.out.println("Mail not found in DB, adding user");
            String query = "INSERT INTO companyPoll (mail, password) VALUES (? , ?)";
            try {
                PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
                ps.setString(1, mail);
                ps.setString(2, Crypto.sha256(defaultPassword));
                ps.executeUpdate();

                Proxy.updateProxy();

                return true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("Mail already exists in DB, not adding user");
        return false;
    }

    /**
     * delete a user from the table
     * @param mail of a user
     * @return true if a user exist in the table with the corresponding mail
     * @throws UserNotConnectedException
     */
    public boolean removeUser(String mail) {
        if(checkIfMailAlreadyExistsInDB(mail)){
            System.out.println("Mail found in DB, removing user");
            String query = "DELETE FROM companyPoll WHERE mail=?";
            try {
                PreparedStatement ps = ServerDB.getCon().prepareStatement(query);
                ps.setString(1, mail);
                ps.executeUpdate();

                Proxy.updateProxy();

                return true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("Mail not found in DB, nothing to remove");
        return false;
    }

    /**
     * To Implement -> Create a new Poll
     */
    public void createPoll(){

    }

    public String getLastName() {
        return lastName;
    }

    public String getTokenSession() {
        return tokenSession;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mail='" + mail + '\'' + ", password='" + password + '\'' +
                ", tokenSession='" + tokenSession + '\'' +
                '+'+connectionState+'}';
    }

    public boolean checkAdmin(){
        return checkIfUserIsAnAdmin();
    }

    public Object getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object user){
        if(!user.getClass().toString().equals(getClass().toString())){
            return false;
        }
        User u = (User) user;
        return getMail().equals(u.getMail()) && getPassword().equals(u.getPassword()) && getTokenSession().equals(u.getTokenSession());


    }

}
