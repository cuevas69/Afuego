package com.example.afuego;

public class Message {
    private String messageId;
    private String senderId;
    private String receiverId;
    private String content;

    public Message() {
        // Constructor vacío requerido para Firebase
    }

    public Message(String senderId, String receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }
}


