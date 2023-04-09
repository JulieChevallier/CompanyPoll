package com.clochelabs;

import com.clochelabs.packet.*;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private ObjectInputStream in; //Stream containing data from the Client
    private ObjectOutputStream out;

    private Socket socket; //Socket waiting for a Client

    //builder for tests
    public ClientHandler() {
    }

    public ClientHandler(Socket socket) {

        try {
            this.socket = socket;
            in = new ObjectInputStream(this.socket.getInputStream());
            out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        try {
            // Get the packet from the client
            Packet request = (Packet) in.readObject();

            System.out.println("[" + new java.util.Date() + "] | Request received: " + request);

            // Get the type of the packet and call the corresponding method
            switch (request.getType()) {
                case AUTH -> out.writeObject(connect((AuthPacket) request));
                case VOTE -> out.writeObject(vote((VotePacket) request));
                case ADDUSER -> out.writeObject(addUser((AddUserPacket) request));
                case SETNAME -> out.writeObject(setFirstName((SetFirstNamePacket) request));
                case REMOVEUSER -> out.writeObject(removeUser((RemoveUserPacket) request));
                case SETLASTNAME -> out.writeObject(setLastName((SetLastNamePacket) request));
                case SETPASSWORD -> out.writeObject(setPassword((SetPasswordPacket) request));
                case DISCONNECTION -> out.writeObject(disconnect((DisconnectionPacket) request));
                case GETKEY -> out.writeObject(sendKey((GetKeyPacket) request));
                case GETPOLL -> out.writeObject(givePoll());
                case ADDPOLL -> out.writeObject(addPoll((AddPollPacket) request));
                case GETRESULTCLIENT -> out.writeObject(giveResultClient((GetResultClientPacket) request));
                case CLOSE -> out.writeObject(closePoll((ClosePollPacket) request));
                case GETUSER -> out.writeObject(giveUser());
                case SETMAIL -> out.writeObject(setMail((SetMailPacket) request));
                case USER_HAS_VOTED -> out.writeObject(userHasVoted((UserHasVotedPacket) request));

                // If the packet type is not recognized, send an error packet
                default -> out.writeObject(new ErrorPacket("Unknown packet type"));
            }

            closeThreadAndSocket();


        } catch (IOException | ClassNotFoundException | UserNotConnectedException e) {
            e.printStackTrace();
        }
    }

    private Packet closePoll(ClosePollPacket request) {
        Urne.getInstance().closeScrutin(request.getIdPoll());
        return new SuccessPacket("poll fermé");
    }

    private GiveResultPacket giveResultClient(GetResultClientPacket request) {
        return new GiveResultPacket(Urne.getInstance().getResultPerRef(request.getIdRef()), Urne.getInstance().getNbVotantPerRef(request.getIdRef()));
    }

    private Packet addPoll(AddPollPacket request) {
        System.out.println("Date fin : " + request.getDateFin());

        Urne.getInstance().newRef(request.getIntitule(), request.getChoix1(), request.getChoix2(), DateUtils.stringToDate(request.getDateFin()));
        return new SuccessPacket("nouveau packet créé");
    }

    private Packet givePoll() {
        return new GivePollPacket(Urne.getInstance().getRef());

    }


    private boolean vote(Integer idVotant, int idScrutin, BigInteger[] vote) {
        if (Urne.getInstance().addVote(vote, idVotant, idScrutin)) {
            return true;
        } else {
            return false;
        }
    }

    private Packet sendKey(GetKeyPacket request) {
        
        try {
            if(Urne.getInstance().getPublicKey() != null) {
                return new GiveKeyPacket(Urne.getInstance().getPublicKey());
            }
        } catch (Exception e) {
            return new ErrorPacket("Error while getting the key : " + e.getMessage());
        }
        return new ErrorPacket("Error while getting the key. The key might be null");
    }

    public Packet userHasVoted(UserHasVotedPacket packet){
        User user = UserRepository.getInstance().getUserByMail(packet.getMail());
        int idScrutin = packet.getIdScrutin();
        if(Urne.getInstance().getRef(idScrutin).userHasVoted(user.getId())){
            return new SuccessPacket("User can vote");
        }
        return new ErrorPacket("User has already voted");
    }


    public Packet vote(VotePacket voteRequest) {
        try {

            // Building a user from the request
            User user = UserRepository.getInstance().getUser(new User(voteRequest.getMail(), voteRequest.getPassword(), voteRequest.getToken()));

            // Checking if the user is connected
            if(!UserRepository.getInstance().isUserConnected(user)){
                return new ErrorPacket("You must be connected to vote");
            }

            // Get info of the scrutin
            Integer idUser = user.getId();
            int idScrutin = voteRequest.getIdScrutin();
            BigInteger[] vote = voteRequest.getVote();

            // Voting
            return vote(idUser, idScrutin, vote) ? new SuccessPacket("Vote accepted") : new ErrorPacket("Vote not valid");

        } catch (Exception e) {
            // The user voted for a scrutin that doesn't exist (Out of bound)
            return (e instanceof IndexOutOfBoundsException) ? new ErrorPacket("Error while voting, the poll doesn't exist") : new ErrorPacket("Error while voting");
        }

    }


    /**
     * <p>Connects the user to the server</p>
     * @return a Packet with the result of the connection
     * @throws UserNotConnectedException
     */
    public Packet connect(AuthPacket authPacket) throws UserNotConnectedException {

        // Getting infos of the packet
        String mail = authPacket.getMail();
        String password = authPacket.getPassword();
        String token = authPacket.getToken();

        // Building a user with the infos
        User user = new User(mail, password, token);

        // Try to connect the user (return a Packet corresponding to the result)
        return tryConnectUser(user);
    }

    public Packet disconnect(DisconnectionPacket request) {

        // Building a user from the request
        User user = new User(request.getMail(), request.getPassword(), request.getToken());

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to disconnect");
        }
    
        // Disconnecting the user
        UserRepository.getInstance().disconnectUser(user);
        return new SuccessPacket("User disconnected");
    }

    public Packet setPassword(SetPasswordPacket request) {

        // Building a user from the request
        User user = new User(request.getMail(), request.getPassword(), request.getToken());
        
        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to change your password");
        }

        // Setting the new password
        try {
            user.setPassword(request.getNewPassword());
            return new SuccessPacket("Password changed with success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorPacket("Password not changed");
        }
    }

    
    public Packet addUser(AddUserPacket request) {

        // Getting infos of the packet
        String mail = request.getMailOfAdder();
        String password = request.getPassword();
        String token = request.getToken();
        
        // Building a user with the infos
        User user = new User(mail, password, token);

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to add a user");
        }

        // Checking if the user is an admin
        if (user.checkAdmin()) {
            // Adding the user
            try {
                if(user.addUser(request.getMailOfAddedUser())){
                    return new SuccessPacket("User added without any issue");
                } else {
                    return new ErrorPacket("User not added, mail already used");
                }
            } catch (Exception i) {
                i.printStackTrace();
                return new ErrorPacket("Error while adding the user");
            }
        }
        // The user is not an admin
        return new ErrorPacket("User is not an admin");
    }
    
    public Packet removeUser(RemoveUserPacket request) {
        
        // Getting infos of the packet
        String mail = request.getMailOfAdder();
        String password = request.getPassword();
        String token = request.getToken();
        
        // Building a user with the infos
        User user = new User(mail, password, token);

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to remove a user");
        }

        // Checking if the user is an admin
        if (user.checkAdmin()) {
            try {
                // Removing the user
                if(user.removeUser(request.getMailOfRemovedUser())){
                    return new SuccessPacket("User removed without any issue");
                } else {
                    return new ErrorPacket("User not removed, mail doesn't exist");
                }
            } catch (Exception i) {
                i.printStackTrace();
                return new ErrorPacket("Error while removing the user");
            }
        }
        // The user is not an admin
        return new ErrorPacket("User is not an admin");
    }

    public Packet setFirstName(SetFirstNamePacket request) {
        
        // Building a user from the request
        User user = new User(request.getMailAdmin(), request.getPassword(), request.getToken());

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to change the first name of a user");
        }

            // Setting the first name
        try {
            user.setFirstName(request.getMail(), request.getFirstname());
            return new SuccessPacket("First name of " + request.getMail() + " changed with success");
        } catch (Exception i) {
            i.printStackTrace();
            return new ErrorPacket("Error while changing the first name");
        }
    }

    public Packet setLastName(SetLastNamePacket request) {

        // Building a user from the request
        User user = new User(request.getMailAdmin(), request.getPassword(), request.getToken());

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to change the last name of a user");
        }
        // Setting the last name
        try {
            user.setLastName(request.getMail(), request.getLastname());
            return new SuccessPacket("Last name of " + request.getMail() + " changed with success");
        } catch (Exception i) {
            i.printStackTrace();
            return new ErrorPacket("Error while changing the last name");
        }
    }

    public Packet setMail(SetMailPacket request) {

        // Building a user from the request
        User user = new User(request.getOldMail(), request.getPassword(), request.getToken());

        // Checking if the user is connected
        if(!UserRepository.getInstance().isUserConnected(user)){
            return new ErrorPacket("You must be connected to change the mail of a user");
        }
        // Setting the mail
        try {
            if(User.checkIfMailAlreadyExistsInDB(request.getNewMail())){
                return new ErrorPacket("Mail already used");
            }
            user.setMail(request.getOldMail(), request.getNewMail());
            if(UserRepository.getInstance().getUserByMail(request.getOldMail()) != null){
                UserRepository.getInstance().disconnectUser(UserRepository.getInstance().getUserByMail(request.getOldMail()));
            }
            return new SuccessPacket("Mail of " + request.getOldMail() + " changed with success");
        } catch (Exception i) {
            i.printStackTrace();
            return new ErrorPacket("Error while changing the mail");
        }
    }

    /**
     * Close the socket, the in DataStream and kills the current thread
     *
     * @throws IOException
     */
    private void closeThreadAndSocket() throws IOException {
        socket.close();
        in.close();
        out.close();
        ServiceManager.threadKilled();
        this.interrupt();
    }

    /**
     * <p>Check if the user is connected.
     */


    /**
     * <p>Check if the user is connected, (only works for connect method)</p>
     */
    private Packet tryConnectUser(User user) {
        try{
            User connectedUser = UserRepository.getInstance().getUserByMail(user.getMail());
    
            // If there is a same user logged in, using the same mail
            if(connectedUser != null){
                
                if(connectedUser.getTokenSession().equals(user.getTokenSession())){
                    // User is already connected
                    return new ConnectionSuccessPacket(user.getFirstName(), user.getLastName(),user.checkIfUserIsAnAdmin());
                } else {
                    // User is already connected with another device
                    return new ErrorPacket("You are already connected with another device");
                }
            }
        }

        catch (Exception e){
            e.printStackTrace();
            return new ErrorPacket("Error while checking if the user is connected");
        }

        // The user is not connected, try to connect him
        if(user.connect()){
            return new ConnectionSuccessPacket(user.getFirstName(), user.getLastName(), user.checkIfUserIsAnAdmin());
        } else {
            return new ErrorPacket("User not connected");
        }
    }



    private GiverUserPacket giveUser(){
        return new GiverUserPacket(UserRepository.getInstance().getUserList());
    }
}
