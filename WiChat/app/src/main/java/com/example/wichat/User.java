package com.example.wichat;

import java.io.Serializable;

public class User implements Serializable {
    private String usernameAcount;
    private String email;
    private String password;
    private int verificationCode;
    private String profilePicture;
//    private String endTime;
//    private String endMessage;

//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }
//
//    public String getEndMessage() {
//        return endMessage;
//    }
//
//    public void setEndMessage(String endMessage) {
//        this.endMessage = endMessage;
//    }

    public User() {
    }

    public User(String usernameAcount, String email, String password, int verificationCode, String profilePicture) {
        this.usernameAcount = usernameAcount;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.profilePicture = profilePicture;
    }
//    public User(String usernameAcount, String email, String password, int verificationCode, String profilePicture, String endTime, String endMessage) {
//        this.usernameAcount = usernameAcount;
//        this.email = email;
//        this.password = password;
//        this.verificationCode = verificationCode;
//        this.profilePicture = profilePicture;
//        this.endTime = endTime;
//        this.endMessage = endMessage;
//    }

    public String getUsernameAcount() {
        return usernameAcount;
    }

    public void setUsernameAcount(String usernameAcount) {
        this.usernameAcount = usernameAcount;
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

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
