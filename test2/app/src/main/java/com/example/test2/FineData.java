package com.example.test2;

public class FineData {
    private String chatRoomName;
    private String sendPerson;
    private String message;

    public FineData() {
    }

    public FineData(String chatRoomName, String sendPerson, String message) {
        this.chatRoomName = chatRoomName;
        this.sendPerson = sendPerson;
        this.message = message;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
