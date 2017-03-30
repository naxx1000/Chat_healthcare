package com.koalacompany.jonaspc.p5_chat_test;

public class DataProvider {

    public String message;
    public String author;

    //constructor
    public DataProvider(String message, String author){
        super();
        this.setMessage(message);
        this.setAuthor(author);
    }

    //Getters and setters
    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
