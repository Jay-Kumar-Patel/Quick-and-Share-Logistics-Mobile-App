package com.example.easy_transport;

public class User_google_signin {

    String email,username,profilepic;

    public User_google_signin(String email, String username, String profilepic) {
        this.email = email;
        this.username = username;
        this.profilepic = profilepic;
    }

    public User_google_signin(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}

