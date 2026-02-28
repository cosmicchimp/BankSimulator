package com.lambert.banksimulator.model;

public class Message {
    private int messageID = -1;
    private String sender = "";
    private String receiver = "";
    private String messageBody = "";
    private String dateString = "";
    public Message(int messageID, String sender, String receiver, String messageBody, String date) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.messageBody = messageBody;
        this.dateString = date;
    }
    public int id() {
        return this.messageID;
    }
    public String message() {
        return this.messageBody;
    }
    public String sender() {
        return this.sender;
    }
    public String receiver() {
        return this.receiver;
    }
    public String date() {
        return this.dateString;
    }
}
