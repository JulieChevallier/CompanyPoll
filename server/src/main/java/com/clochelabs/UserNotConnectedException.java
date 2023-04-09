package com.clochelabs;

public class UserNotConnectedException extends Exception {
    private String message;

    public UserNotConnectedException(String message1) {
        this.message = message1;
    }

    public String getMessage(){
        return message;
    }
}
