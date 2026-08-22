package com.example.chatbox;

import com.google.firebase.Timestamp;

public class Message {
    private String id;
    private String text;
    private String senderId;
    private String senderName;
    private Timestamp timestamp;

    public Message() {
        // Required for Firestore
    }

    public Message(String id, String text, String senderId, String senderName, Timestamp timestamp) {
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}