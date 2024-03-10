package com.example.my_whatsapp.Models;

public class Users {


    String userId;
    String username;
    String Profile_picture;
    String email;
    String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfile_picture() {
        return Profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        Profile_picture = profile_picture;
    }

    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}




    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public Users(String profile_picture, String email, String password,  String lastmessage,String username,String userId) {
        Profile_picture = profile_picture;
        this.email = email;
        this.password = password;
        this.username=username;
        this.lastmessage = lastmessage;
        this.userId=userId;
    }

    public Users(){
    }

    //constructor for Signup

    public Users(String username,String email,String password){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    String lastmessage;

}


