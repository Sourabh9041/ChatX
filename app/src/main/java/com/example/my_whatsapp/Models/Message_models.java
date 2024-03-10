package com.example.my_whatsapp.Models;

public class Message_models {

    String uId;
    String message;
    String timestamp;

    public Message_models(String uId, String message, String timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Message_models() {

    }

    public Message_models(String message, String uId) {

        this.uId = uId;
        this.message = message;

    }

    public Message_models(String message) {

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
