package com.schoolmanagement.model;

public class Admin {

private String username;
private String password;

public Admin (String username, String password){
    this.username = username;
    this.password = password;
}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void displayAdmin(){
    System.out.println("Username: " + username); // no password yet cause we dont want it being displayed on the screen
    }
}