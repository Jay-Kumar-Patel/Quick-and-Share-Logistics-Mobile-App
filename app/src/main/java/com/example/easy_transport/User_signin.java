package com.example.easy_transport;

public class User_signin {

    String emailid,password,username;

    public User_signin(String emailid, String password, String username) {
        this.emailid = emailid;
        this.password = password;
        this.username = username;
    }

    public User_signin(){}

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
