package com.koalacompany.jonaspc.p5_chat_test;

public class DataProvider {

    public String message;
    public String timestamp;
    public String author;

    //constructor
    public DataProvider(String message, String timestamp, String author){
        super();
        this.setMessage(message);
        this.setAuthor(author);
        this.setTimestamp(timestamp);
    }

    //Getters and setters
    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
